package twentythree.dayTwenty

import util.lcm
import util.transpose
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwenty/file.txt"))

fun main() {
    println(part1(input))
    println(part2(input))
}

private enum class State {
    OFF, ON;

    operator fun not() = if (this == ON) OFF else ON
}

private data class Pulse(val from: Module, val state: State, val to: Module) {
    fun send() = to.process(this)
}

private abstract class Module(open val name: String, open var outputs: List<Module>) {
    open val state: State get() = State.OFF
    abstract fun process(pulse: Pulse): List<Pulse>
    open fun addInput(input: Module) = Unit
}

private data class FlipFlop(override val name: String, override var outputs: List<Module>) : Module(name, outputs) {
    override var state: State = State.OFF
    private val cachedLowPulses by lazy(LazyThreadSafetyMode.NONE) { outputs.map { Pulse(this, State.OFF, it) } }
    private val cachedHighPulses by lazy(LazyThreadSafetyMode.NONE) { outputs.map { Pulse(this, State.ON, it) } }
    override fun process(pulse: Pulse): List<Pulse> = if (pulse.state == State.OFF) {
        state = !state
        if (state == State.OFF) cachedLowPulses else cachedHighPulses
    } else emptyList()
}

private data class Conjunction(override val name: String, override var outputs: List<Module>) : Module(name, outputs) {
    private val inputs = mutableMapOf<String, Pulse>()
    private val cachedLowPulses by lazy(LazyThreadSafetyMode.NONE) { outputs.map { Pulse(this, State.OFF, it) } }
    private val cachedHighPulses by lazy(LazyThreadSafetyMode.NONE) { outputs.map { Pulse(this, State.ON, it) } }
    override fun process(pulse: Pulse): List<Pulse> {
        inputs[pulse.from.name] = pulse
        return if (inputs.values.all { it.state == State.ON }) cachedLowPulses else cachedHighPulses
    }
    override fun addInput(input: Module) {
        inputs[input.name] = Pulse(input, State.OFF, this)
    }
}

private data class Broadcast(override var outputs: List<Module>) : Module("broadcaster", outputs) {
    override fun process(pulse: Pulse): List<Pulse> = outputs.map { Pulse(this, pulse.state, it) }
}

private data class Button(private val broadcast: Broadcast) : Module("<button>", listOf(broadcast)) {
    override fun process(pulse: Pulse): List<Pulse> = listOf(Pulse(this, State.OFF, broadcast))
}

private class Receiver : Module("rx", emptyList()) {
    var isActivated = false
    override fun process(pulse: Pulse): List<Pulse> {
        isActivated = isActivated || pulse.state == State.OFF
        return emptyList()
    }
}

private data class Dummy(override val name: String) : Module(name, emptyList()) {
    override fun process(pulse: Pulse): List<Pulse> = emptyList()
}

private val DUMMY_PULSE = Pulse(Dummy("dummy"), State.ON, Dummy("dummy"))

private fun Button.pressAndCount(): Pair<Long, Long> {
    var numLowPulses = 0L
    var numHighPulses = 0L
    val pulses = LinkedList<Pulse>().apply { addAll(process(DUMMY_PULSE)) }

    while (pulses.isNotEmpty()) {
        val pulse = pulses.poll()
        if (pulse.state == State.OFF) ++numLowPulses else ++numHighPulses
        pulse.send().let(pulses::addAll)
    }

    return numLowPulses to numHighPulses
}

private fun Button.press(pressesCount: Long, prevs: MutableMap<String, Long>, cycles: MutableMap<String, Long>) {
    val pulses = LinkedList<Pulse>().apply { addAll(process(DUMMY_PULSE)) }

    while (pulses.isNotEmpty()) {
        val pulse = pulses.poll()
        val pulseName = pulse.to.name

        if (pulse.state == State.OFF && pulseName in prevs) {
            prevs[pulseName]?.let { cycles[pulseName] = pressesCount - it }
            prevs[pulseName] = pressesCount
        }

        pulse.send().let(pulses::addAll)
    }
}

private val CONFIG_LINE_REGEX =
    """\s*(?:(?<bc>broadcaster)|(?<code>[%&])(?<name>[a-z]+))\s*->\s*(?<outputs>[a-z]+(?:\s*,\s*[a-z]+)*)\s*"""
        .toRegex()

private fun parseConfiguration(input: List<String>): Map<String, Module> {
    val modules = input.map { line ->
        val matchResult = CONFIG_LINE_REGEX.matchEntire(line) ?: error("Cannot parse configuration line $line")
        val outputs = matchResult.groups["outputs"]
            ?.value
            ?.split(',')
            ?.map(String::trim)
            ?.map { if (it == "rx") Receiver() else Dummy(it) }
            ?: error("Unexpected state: outputs are absent in $line")

        if (matchResult.groups["bc"] != null) {
            Broadcast(outputs)
        } else {
            val code = matchResult.groups["code"]?.value
            val name = matchResult.groups["name"]?.value ?: error("Unexpected state: name is absent in $line")
            when (code) {
                "%" -> FlipFlop(name, outputs)
                "&" -> Conjunction(name, outputs)
                else -> error("Unexpected state: wrong code ($code) in $line")
            }
        }
    }.associateBy { it.name }

    modules.values.forEach { module ->
        module.outputs = module.outputs.map { modules[it.name] ?: it }.onEach { it.addInput(module) }
    }

    return modules
}

private fun part1(input: List<String>): Long {
    val button = Button(parseConfiguration(input)["broadcaster"] as Broadcast)

    return (1..1000).fold(0L to 0L) { acc, _ ->
        val (numLow, numHigh) = button.pressAndCount()
        acc.first + numLow to acc.second + numHigh
    }.let { it.first * it.second }
}

private fun part2(input: List<String>): Long {
    val modules = parseConfiguration(input)
    val button = Button(modules["broadcaster"] as Broadcast)
    val receiver = modules.values.flatMap(Module::outputs).find { it.name == "rx" } as Receiver
    var count = 0L

    val inverse = modules.mapValues { (_, module) -> module.outputs.map(Module::name) }.transpose()
    val prevs = inverse.getValue(inverse.getValue("rx").first()).associateWith { 0L }.toMutableMap()
    val cycles = mutableMapOf<String, Long>()

    while (!receiver.isActivated) {
        button.press(++count, prevs, cycles)
        if (cycles.size == prevs.size) {
            return lcm(cycles.values.map { it.toBigInteger() }).toLong()
        }
    }

    return count
}




