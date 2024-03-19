package year2015.day1

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day1/file.txt"))

fun main() {
    val solution = Day15Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

class Day15Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
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
