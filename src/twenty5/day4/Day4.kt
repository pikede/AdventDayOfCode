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

    override fun part1(): Int {
        var count = 0
        for (r in quizInput.indices) {
            for (c in quizInput[r].indices) {
                if(quizInput[r][c] != '@') continue
                var currentRollsOfPaper = 0
                for (direction in Direction.all8()) {
                    val newR = r + direction.y
                    val newC = c + direction.x
                    if (newR in quizInput.indices && newC in quizInput[newR].indices && quizInput[newR][newC] == '@') {
                        currentRollsOfPaper++
                    }
                }
                if (currentRollsOfPaper < 4) count++
            }
        }
        return count
    }

    override fun part2(): Int {
        var count = 0
        val toRemove = mutableSetOf<Pair<Int, Int>>()
        val quizInputArr = quizInput.map { it.toCharArray() }.toMutableList()
        do {
            toRemove.forEach {
                val (r, c) = it
                quizInputArr[r][c] = '.'
            }
            toRemove.clear()
            for (r in quizInputArr.indices) {
                for (c in quizInputArr[r].indices) {
                    if (quizInputArr[r][c] != '@') continue
                    var currentRollsOfPaper = 0
                    for (direction in Direction.all8()) {
                        val newR = r + direction.y
                        val newC = c + direction.x
                        if (newR in quizInputArr.indices && newC in quizInputArr[newR].indices && quizInputArr[newR][newC] == '@') {
                            currentRollsOfPaper++
                        }
                    }
                    if (currentRollsOfPaper < 4) {
                        count++
                        toRemove += r to c
                    }
                }
            }

        } while (toRemove.isNotEmpty())
        return count
    }
}