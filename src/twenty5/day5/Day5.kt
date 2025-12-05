package twenty5.day5

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day5/file.txt"))

private fun main() {
    println(Day5.part1())   // 525
    println(Day5.part2())   // 333892124923577
}


private object Day5 : AOCPuzzle {
    private val ranges = mutableListOf<LongRange>()
    private val ids = mutableListOf<Long>()

    init {
        getRangesAndIDs()
    }

    private fun getRangesAndIDs() {
        var isRange = true
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
    }

    override fun part1(): Long {
        var count = 0L
        for (id in ids) {
            if (ranges.any { id in it }) {
                count++
            }
        }
        return count
    }

    override fun part2(): Long {
        return ranges
            .distinctBy { it.first..it.last } // removes duplicate Id ranges
            .sortedBy { it.first }  // sorts by starting range
            .fold(emptyList<LongRange>()) { acc, nextRange -> // merges the overlapping ranges
                val lastRange = acc.lastOrNull()
                when {
                    lastRange == null -> listOf(nextRange)
                    lastRange.last < nextRange.first -> acc + listOf(nextRange)
                    else -> {
                        acc.dropLast(1) + listOf(lastRange.first..maxOf(lastRange.last, nextRange.last))
                    }
                }
            }.fold(0L) { acc, next -> // using fold here as a workaround for sumBy
                acc + next.last - next.first + 1
            }
    }
}