package twenty5.day4

import AOCPuzzle
import org.aoc.utils.readInput
import utils.Direction
import org.aoc.utils.readInput

private val quizInput = readInput("twenty5/day4/file")

private fun main() {
    println(Day4.part1()) // 1435
    println(Day4.part2()) // 8623
}

private object Day4 : AOCPuzzle {
    val removableRollsOfPaper = mutableSetOf<Pair<Int, Int>>()
    val quizInputArr = quizInput.map { it.toCharArray() }.toMutableList()

    override fun part1(): Int { // time n * m | space 1
        var totalAccessibleRollsOfPaper = 0
        for (r in quizInputArr.indices) {
            for (c in quizInputArr[r].indices) {
                if (quizInputArr[r][c] != '@') {
                    continue
                }
                var currentAccessibleRollsOfPaper = 0
                for (direction in Direction.all8()) {
                    val newR = r + direction.y
                    val newC = c + direction.x
                    if (newR in quizInputArr.indices && newC in quizInputArr[newR].indices && quizInputArr[newR][newC] == '@') {
                        currentAccessibleRollsOfPaper++
                    }
                }
                if (currentAccessibleRollsOfPaper < 4) {
                    totalAccessibleRollsOfPaper++
                    removableRollsOfPaper += r to c
                }
            }
        }
        return totalAccessibleRollsOfPaper
    }

    override fun part2(): Int { // time (n * m) ^ 2  | space n * m
        var count = part1()

        do {
            removableRollsOfPaper.forEach {
                val (r, c) = it
                quizInputArr[r][c] = '.'
            }
            removableRollsOfPaper.clear()
            count += part1()
        } while (removableRollsOfPaper.isNotEmpty())

        return count
    }
}