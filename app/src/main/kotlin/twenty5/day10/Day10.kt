package twenty5.day10

import AOCPuzzle
import com.microsoft.z3.*
import java.nio.file.Files
import java.nio.file.Paths

private typealias Buttons = MutableList<Int>

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("/Users/prince/Desktop/AdventDayOfCode2020/app/src/main/kotlin/twenty5/day10/file.txt"))

private fun main() {
    println(Day10.part1())  //  514
    println(Day10.part2())  // 21824
}

private object Day10 : AOCPuzzle {

    private val requirements = quizInput.map {
        JoltageRequirements.from(it)
    }

    override fun part1(): Any {
        val minCounts = mutableListOf<Int>()
        requirements.forEach {
            val buttonPresses = mutableListOf<Buttons>()
            val counts = mutableListOf<Int>(Int.MAX_VALUE)
            getFewestButtonPresses(
                it.buttons,
                it.indicators,
                buttonPresses,
                String(it.emptyIndicators),
                counts,
                0
            )
            val min = counts.min() ?: Int.MIN_VALUE
            minCounts += min
        }
        println(minCounts)
        return minCounts.sum()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getFewestButtonPresses(
        availableButtons: List<Buttons>,
        targetIndicator: String,
        pressedButtons: MutableList<Buttons>,
        currentIndicator: String,
        counts: MutableList<Int>,
        count: Int
    ) {
        if (counts[0] == 1) return
        if (counts.min()!! <= count) return
        if (currentIndicator == targetIndicator) {
            counts[0] = minOf(count, counts[0])
            return
        }
        if (pressedButtons.size > 10) return

        for (i in availableButtons) {
            val next = currentIndicator.applyButtons(i)
            pressedButtons.add(i)
            getFewestButtonPresses(
                availableButtons,
                targetIndicator,
                pressedButtons,
                next,
                counts,
                count + 1
            )
            pressedButtons.removeLast()
        }
    }

    private fun String.applyButtons(buttons: List<Int>): String {
        val arr = this.toCharArray()
        for (i in buttons) {
            arr[i] = if (this[i] == '.') '#' else '.'
        }
        return String(arr)
    }

    override fun part2(): Any {
        return requirements.fold(0L) { acc, joltageRequirements -> acc + joltageRequirements.enableVoltages() }
    }

    private data class JoltageRequirements(
        val indicators: String,
        val buttons: List<Buttons>,
        val voltages: List<Int>
    ) {
        val emptyIndicators = CharArray(indicators.length) { '.' }


        fun enableVoltages(): Int {
            return Context().use { ctx ->
                val opt = ctx.mkOptimize()

                fun intVariableOf(name: String) = ctx.mkIntConst(name)

                fun intValueOf(value: Int) = ctx.mkInt(value)

                infix fun IntExpr.gte(t: IntExpr) = ctx.mkGe(this, t)

                operator fun ArithExpr<IntSort>.plus(t: IntExpr) = ctx.mkAdd(this, t)

                infix fun ArithExpr<IntSort>.equalTo(t: IntExpr) = ctx.mkEq(this, t)

                fun List<IntExpr>.sum() = ctx.mkAdd(*this.toTypedArray())

                val ZERO = intValueOf(0)

                val affectedJoltages = mutableMapOf<Int, MutableList<IntExpr>>()
                val buttonVariables = this.buttons.mapIndexed { index, schematic ->
                    // create button variable
                    intVariableOf(index.toString()).also { buttonVariable ->
                        // track the joltages if affects
                        schematic.forEach { affectedIndex ->
                            affectedJoltages.computeIfAbsent(affectedIndex) { _ -> mutableListOf() }.add(buttonVariable)
                        }
                        // add it to the optimizer
                        opt.Add(buttonVariable gte ZERO)
                    }
                }

                affectedJoltages.forEach { (voltageIndex, buttonsAffecting) ->
                    val targetValue = intValueOf(voltages[voltageIndex])
                    val sumOfButtonPresses = buttonsAffecting.sum()
                    opt.Add(sumOfButtonPresses equalTo targetValue)
                }

                opt.MkMinimize(buttonVariables.sum())

                val status = opt.Check()

                if (status == Status.SATISFIABLE) {
                    val model = opt.getModel()
                    ((model.evaluate(buttonVariables.sum(), true) as IntNum).int)
                } else {
                    throw IllegalStateException("No Solution Found")
                }
            }
        }
        companion object {
            fun from(input: String): JoltageRequirements {
                val first = input.indexOf(']')
                val last = input.indexOf("{")
                val indicator = input.substring(1, first)
                val joltage = input.substring(last + 1).replace("}", "").trim().split(",").map { it.toInt() }
                val buttons = input.substring(first + 1, last).split(" ")
                    .map {
                        it.drop(1).dropLast(1)
                            .split(",")
                            .filter { it.isNotEmpty() }
                            .map { it.toInt() } as Buttons
                    }
                    .filter { it.isNotEmpty() }
                return JoltageRequirements(indicator, buttons, joltage)
            }
        }
    }
}

