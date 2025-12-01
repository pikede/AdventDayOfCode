package twenty5.day2

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day2/file.txt"))

private fun main() {
    println(Day2.part1()) // 1158
    println(Day2.part2()) // 6860
}

private object Day2 : AOCPuzzle {

    override fun part1(): Int {
        return 0
    }

    override fun part2(): Int {
        return 0
    }

}