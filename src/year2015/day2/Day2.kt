package year2015.day2

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day2/file.txt"))

fun main() {
    val solution = Day2Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

class Day2Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        TODO("Not yet implemented")
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}
