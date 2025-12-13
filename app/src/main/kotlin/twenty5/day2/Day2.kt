package twenty5.day2

import AOCPuzzle
import org.aoc.utils.readInput
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput = readInput("twenty5/day2/file")

private fun main() {
    println(Day2.part1()) //31839939622
    println(Day2.part2()) //41662374059
}

private object Day2 : AOCPuzzle {

    override fun part1(): Long {
        var sum = 0L
        val ids = quizInput[0].split(",")
        ids.forEach { currentId ->
            val (start, end) = getStartAndEndIds(currentId)
            for (i in start..end) {
                val stringValue = i.toString()
                val half = stringValue.length
                if (half % 2 != 0) continue
                val firstHalf = stringValue.substring(0, half / 2)
                val secondHalf = stringValue.substring(half / 2)
                if (firstHalf == secondHalf) {
                    sum += i
                }
            }
        }
        return sum
    }

    override fun part2(): Long {
        var sum = 0L
        val ids = quizInput[0].split(",")
        ids.forEach { currentIdRange ->
            val (start, end) = getStartAndEndIds(currentIdRange)
            outerLoop@ for (currentId in start..end) {
                val stringValue = currentId.toString()
                val charArray = stringValue.toCharArray()
                val length = charArray.size
                val half = stringValue.length
                // handles ids that are less than 2 i.e. 0 -> 99
                if (length == 2) {
                    if (half % 2 != 0) continue
                    val firstHalf = stringValue.substring(0, half / 2)
                    val secondHalf = stringValue.substring(half / 2)
                    if (firstHalf == secondHalf) {
                        sum += currentId
                        continue@outerLoop
                    }
                } else {
                    // handles ids that are greater than 2 in length i.e. 100 -> Long.MAX_VALUE
                    sum += getValidId(currentId, length)
                }

            }
        }
        return sum
    }

    private fun getStartAndEndIds(currentIdRange: String) = currentIdRange.split("-").map { it.toLong() }

    private fun getValidId(i: Long, length: Int): Long {
        val stringValue = i.toString()
        innerLoop@ for (halves in 2 until length) {
            val first = stringValue.substring(0, length / halves)
            var startIndex = first.length
            while (startIndex < length) {
                val endIndex = minOf(startIndex + first.length, length)
                if (first != stringValue.substring(startIndex, endIndex)) {
                    continue@innerLoop
                }
                startIndex += first.length
            }
            return i
        }
        return 0
    }
}