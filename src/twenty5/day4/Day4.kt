package twenty5.day3

import AOCPuzzle
import utils.Direction
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day4/file.txt"))

private fun main() {
    println(Day4.part1()) // 1435
    println(Day4.part2()) // 8623
}

private object Day4 : AOCPuzzle {
    val removableRollsOfPaper = mutableSetOf<Pair<Int, Int>>()
    val quizInputArr = quizInput.map { it.toCharArray() }.toMutableList()

    override fun part1(): Int {
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

    override fun part2(): Int {
        var count = part1()

        while (removableRollsOfPaper.isNotEmpty()) {
            removableRollsOfPaper.forEach {
                val (r, c) = it
                quizInputArr[r][c] = '.'
            }
            removableRollsOfPaper.clear()
            count += part1()
        }
        return count
    }
}