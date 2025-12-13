package twenty5.day5

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput = readInput("twenty5/day5/file")

private fun main() {
    println(Day5.part1())   // 525
    println(Day5.part2())   // 333892124923577
}

private object Day5 : AOCPuzzle {
    override fun part1(): Int {
        var isRange = true
        val ranges = mutableListOf<LongRange>()
        val ids = mutableListOf<Long>()

        for (input in quizInput) {
            if (input.isEmpty()) {
                isRange = false
                continue
            }
            when {
                isRange -> {
                    val (start, end) = input.split("-").map { it.toLong() }
                    ranges.add(LongRange(start, end))
                }

                else -> {
                    ids.add(input.toLong())
                }
            }
        }
        return ids.count { id -> ranges.any { longRange -> id in longRange } }
    }

    override fun part2(): Long {
        return quizInput
            .takeWhile { it.isNotEmpty() } // stops at the empty line before the actual ids are
            .map { it.convertsInputLineToLongRange() } // creates the ranges
            .sortedBy { it.start }  // sorts by starting range
            .mergeOverLappingRange()
            .fold(0L) { acc, next -> // using fold here as a workaround for sumBy
                acc + (next.endInclusive - next.start) + 1
            }
    }

    private fun <T : Comparable<T>> List<ClosedRange<T>>.mergeOverLappingRange(): List<ClosedRange<T>> =
        this.sortedBy { it.start } // sorts the ranges by start point
            .fold(emptyList<ClosedRange<T>>()) { acc, nextRange -> // merges the overlapping ranges
                val lastRange = acc.lastOrNull()
                when {
                    lastRange == null -> acc + nextRange // first one i.e. acc is empty
                    lastRange.endInclusive < nextRange.start -> acc + nextRange // no overlap
                    else -> {
                        // merges overlap
                        acc.dropLast(1) + listOf(lastRange.start..maxOf(lastRange.endInclusive, nextRange.endInclusive))
                    }
                }
            }

    private fun String.convertsInputLineToLongRange(): ClosedRange<Long> = this.split("-")
        .map(String::toLong)
        .let { it.first()..it.last() }

}