package twenty5.day6

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day6/file.txt"))

private fun main() {
    println(Day6.part1())   /// 4693159084994
    println(Day6.part2())

}

private object Day6 : AOCPuzzle {
    override fun part1(): Long {
        val numbers = quizInput
            .takeWhile { !it.hasSign() }
            .map {
                it.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
            }
        val signs = quizInput.first { it.hasSign() }.split(" ").filter { it.isNotEmpty() }

        var total = 0L
        signs.forEachIndexed { index, sign ->
            var current = if (sign == "+") 0L else 1L
            when {
                sign == "+" -> numbers.onEach { current += it[index] }
                else -> numbers.onEach { current *= it[index] }
            }
            total += current
        }
        return total
    }

    override fun part2(): Long {
        return 0
    }

    private fun String.isInt(): Boolean {
        return runCatching { this.toInt() is Int }.onSuccess { true }.onFailure { false }.getOrNull() ?: false
    }

    private fun String.hasSign() = '+' in this || '*' in this
}