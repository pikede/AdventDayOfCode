package twenty5.day3

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day3/file.txt"))

private fun main() {
    println(Day3.part1()) // 16854
    println(Day3.part2())
}

private object Day3 : AOCPuzzle {

    override fun part1(): Long {
        var joltageSum = 0L
        quizInput.forEach {
            val batteries = it.toCharArray()
            var maxBattery = 0
            for (i in batteries.indices) {
                for (k in i + 1..batteries.lastIndex) {
                    val firstBattery = batteries[i] - '0'
                    val secondBattery = batteries[k] - '0'
                    val joltage = (firstBattery * 10) + secondBattery
                    maxBattery = maxOf(maxBattery, joltage)
                }
            }
            println(maxBattery)
            joltageSum += maxBattery
        }
        return joltageSum
    }

    override fun part2(): Long {
        return 0
    }
}