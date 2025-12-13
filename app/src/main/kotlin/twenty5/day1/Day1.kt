package twenty5.day1

import AOCPuzzle
import org.aoc.utils.readInput
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput= readInput("twenty5/day1/file")

private fun main() {
    println(Day1.part1()) // 1158
    println(Day1.part2()) // 6860
}

private object Day1 : AOCPuzzle {

    override fun part1(): Int {
        var start = 50
        var countZeroes = 0
        for (turn in quizInput) {
            val direction = turn[0]
            val rotations = turn.drop(1).toInt()
            when {
                direction == 'L' -> start -= rotations  // L rotate Left
                else -> start += rotations // R rotate Right
            }
            if (start < 0) {
                while (start < 0) start += 100
            } else if (start >= 100) {
                while (start >= 100) start -= 100
            }
            if (start == 0) countZeroes++
        }
        return countZeroes
    }

    override fun part2(): Int {
        var currentPosition = 50
        var countZeroes = 0
        for (turn in quizInput) {
            val direction = turn.first()
            val rotations = turn.drop(1).toInt()
            when { // L rotate Left
                direction == 'L' -> {
                    for (i in 0 until rotations) {
                        currentPosition--
                        if (currentPosition < 0) currentPosition += 100
                        if (currentPosition == 0) countZeroes++
                    }
                }

                else -> { // R rotate right
                    for (i in 0 until rotations) {
                        currentPosition++
                        if (currentPosition >= 100) currentPosition -= 100
                        if (currentPosition == 0) countZeroes++
                    }
                }
            }
        }
        return countZeroes
    }

}