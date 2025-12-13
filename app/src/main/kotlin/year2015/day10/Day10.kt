package year2015.day10

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day10/file").toMutableList()

fun main() {
    val solution = Day10Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day10Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        return runGameNTimes(40)
    }

    override fun part2(): Any {
        return runGameNTimes(50)
    }

    fun runGameNTimes(n: Int): Int {
        val currentLevel = StringBuilder(puzzleInput[0])
        repeat(n) {
            var i = 0
            val nextLevel = buildString {
                while (i in currentLevel.indices) {
                    var count = 1
                    val say = currentLevel[i]
                    while (i + 1 in currentLevel.indices && currentLevel[i] == currentLevel[i + 1]) {
                        i++
                        count++
                    }
                    append("$count$say")
                    i++
                }
            }
            currentLevel.clear()
            currentLevel.append(nextLevel)
        }
        return currentLevel.length
    }
}

