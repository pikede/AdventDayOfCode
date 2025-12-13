package twenty4.day24

import org.aoc.utils.readInput
import utils.maxOf

private val quizInput = readInput("twenty4/day24/file")

private fun main() {
    println(crossedWires(quizInput))
}

private fun crossedWires(input: List<String>): Any {
    val commands = mutableListOf<List<String>>()
    val regs = hashMapOf<String, Int>()

    var isCommand = false
    for (i in input) {
        if (i.isEmpty()) {
            isCommand = true
            continue
        }
        if (isCommand) {
            commands += i.split(" ")
        } else {
            val (x, y) = i.split(": ")
            regs[x] = y.toInt()
        }
    }

    fun exec(x: Int, y: Int, op: String, z: String) = when (op) {
        "XOR" -> regs[z] = x xor y
        "OR" -> regs[z] = x or y
        else -> regs[z] = x and y
    }

    val set = mutableSetOf<Int>()
    while (set.size < commands.size) {
        commands.forEachIndexed { i, line ->
            val (x, op, y, _, z) = line
            val (xx, yy) = regs[x] to regs[y]
            if (xx == null || yy == null || !set.add(i)) return@forEachIndexed
            exec(xx, yy, op, z)
        }
    }

    val part1 = regs.filterKeys { it.startsWith("z") }
        .entries.sortedByDescending { it.key }
        .map { it.value }
        .joinToString("")
        .toLong(2)

    val lastZ = commands.map { it.last() }.maxOf { it }
    val wrong = mutableSetOf<String>()

    commands.forEach { (x, op, y, _, z) ->
        if (z.startsWith('z') && op != "XOR" && z != lastZ) wrong.add(z)
        if (op == "XOR" && listOf(x, y, z).all { it.first() !in "zyx" }) wrong.add(z)

        if (op == "AND" && x != "x00" && y != "x00") {
            commands.forEach { (xx, op2, yy) -> if ((z == xx || z == yy) && op2 != "OR") wrong.add(z) }
        }

        if (op == "XOR") {
            commands.forEach { (xx, op2, yy) -> if ((z == xx || z == yy) && op2 == "OR") wrong.add(z) }
        }
    }
    val part2 = wrong.sorted().joinToString(",")

    return part1 to part2
}
