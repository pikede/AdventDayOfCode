package twenty5.day5

import AOCPuzzle
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.sign

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day5/file.txt"))

private fun main() {
    println(Day5.part1())        // 525
    println(Day5.part2())       // 333892124923577
}


private object Day5 : AOCPuzzle {

    private data class IDRange(var start: Long, var end: Long) {
        val freshIdRangeCount = (end - start) + 1
        fun isIDWithinRange(id: Long) = id in start..end
    }

    private val ranges = mutableListOf<IDRange>()
    private val ids = mutableListOf<Long>()
    private var isRange = true

    init {
        for (input in quizInput) {
            if (input.isEmpty()) {
                isRange = false
                continue
            }
            when {
                isRange -> {
                    val (start, end) = input.split("-").map { it.toLong() }
                    ranges.add(IDRange(start, end))
                }

                else -> {
                    ids.add(input.toLong())
                }
            }
        }
    }

    override fun part1(): Long {
        var count = 0L
        for (id in ids) {
            for (range in ranges) {
                if (range.isIDWithinRange(id)) {
                    count++
                    break
                }
            }
        }
        return count
    }

    override fun part2(): Long {
        ranges.sortWith(compareBy { it.start })
        val mergedRanges = mutableListOf<IDRange>()
        mergedRanges.add(ranges.first())

        for (i in 1..ranges.lastIndex) {
            val last = mergedRanges.last()
            val current = ranges[i]
            if (last.end >= current.start) {
                val start = minOf(last.start, current.start)
                val end = maxOf(current.end, last.end)
                mergedRanges[mergedRanges.lastIndex] = IDRange(start, end)
            } else {
                mergedRanges.add(current)
            }
        }

        var count = 0L
        mergedRanges.forEach {
            count += it.freshIdRangeCount
        }
        return count

    }
}