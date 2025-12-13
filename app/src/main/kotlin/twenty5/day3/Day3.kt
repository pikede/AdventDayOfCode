package twenty5.day3

import AOCPuzzle
import org.aoc.utils.readInput
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput= readInput("twenty5/day3/file")

private fun main() {
    println(Day3.part1())   // 16854
    println(Day3.part2())  // 167526011932478
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
            joltageSum += maxBattery
        }
        return joltageSum
    }

    override fun part2(): Long {
        var joltageSum = 0L
        quizInput.forEach {
            val batteries = it.toCharArray()
            joltageSum += getMaxVoltage(batteries)
        }
        return joltageSum
    }

    fun getMaxVoltage(batteries: CharArray): Long {
        val k = 12
        val stack = CharArray(k)
        var top = -1

        val n = batteries.size
        var remaining = n

        for (i in 0 until n) {
            val currentBattery = batteries[i]

            // while we can remove the previous digit to get a bigger result
            while (top >= 0 && stack[top] < currentBattery && (top + 1 + remaining - 1) >= k) {
                top--
            }

            // push if we still need more digits
            if (top + 1 < k) {
                stack[++top] = currentBattery
            }

            remaining--
        }

        // convert to long
        var result = 0L
        for (i in 0 until k) {
            result = result * 10 + (stack[i] - '0')
        }
        return result
    }


}