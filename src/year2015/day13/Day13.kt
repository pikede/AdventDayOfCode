package year2015.day13

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day13/file.txt"))

fun main() {
    val solution = Day13Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day13Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        return 0
    }

    override fun part2(): Any {
        return 0
    }
}
