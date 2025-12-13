package year2015.day1

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day1/file").toMutableList()

fun main() {
    val solution = Day1Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day1Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Int {
        return puzzleInput.fold(0) { acc, curr ->
            val currentFloor = curr.count { it == '(' } - curr.count { it == ')' }
            acc + currentFloor
        }
    }

    override fun part2(): Any {
        puzzleInput.forEach { element ->
            var floor = 0
            element.forEachIndexed { index, parenthesis ->
                floor += when (parenthesis) {
                    '(' -> 1
                    ')' -> -1
                    else -> throw IllegalArgumentException("This is not a '(' or a ')'")
                }
                if (floor == -1) {
                    return index + 1
                }
            }
        }
        throw Exception("Not Found")
    }
}
