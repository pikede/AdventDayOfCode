package year2015.day10

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day10/file.txt"))

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

    fun runGameNTimes(n: Int) : Int {
        val sb = StringBuilder(puzzleInput[0])
        repeat(n) {
            var i = 0
            val temp = StringBuilder()
            while (i in sb.indices) {
                var count = 1
                val say = sb[i]
                while (i + 1 in sb.indices && sb[i] == sb[i + 1]) {
                    i++
                    count++
                }
                temp.append("$count$say")
                i++
            }
            sb.clear()
            sb.append(temp.toString())
        }
        return sb.length
    }
}

