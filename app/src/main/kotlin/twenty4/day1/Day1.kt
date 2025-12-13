package twenty4.day1

import AOCPuzzle
import org.aoc.utils.readInput
import kotlin.math.abs

private val quizInput= readInput("twenty4/day1/file")

private fun main() {
    val day1 = Day1
    println(day1.part1())
    println(day1.part2())
}

private object Day1 : AOCPuzzle {
    private val prefix = quizInput.map { it.split(" ").first().toInt() }.sorted()
    private val suffix = quizInput.map { it.split(" ").last().toInt() }.sorted()

    override fun part1(): Int {
        var sum = 0
        for (i in prefix.indices) {
            sum += abs(prefix[i] - suffix[i])
        }
        return sum
    }

    override fun part2(): Int {
        var sum = 0
        for (i in prefix.indices) {
            val count = suffix.count { it == prefix[i] }
            sum += prefix[i] * count
        }
        return sum
    }

}