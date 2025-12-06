package twenty5.day5

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day6/file.txt"))

private fun main() {
    println(Day6.part1())
    println(Day6.part2())
}

private object Day6 : AOCPuzzle {
    override fun part1(): Int {
        return 0
    }

    override fun part2(): Long {
        return 0
    }
}