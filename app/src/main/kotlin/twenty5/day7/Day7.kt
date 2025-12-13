package twenty5.day7

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput: List<String> = readInput("twenty5/day7/file")

private fun main() {
    println(Day7.part1())   // 1678
    println(Day7.part2())   // 357525737893560
}

private object Day7 : AOCPuzzle {
    private val col = quizInput.first { it.contains('S') }.indexOf('S')

    // only lines on the even indexes can have a splitter, these are the important lines
    private val splitIndexes = quizInput.filterIndexed { index, _ -> index % 2 == 0 }

    override fun part1(): Int {
        val splitterColumns = mutableSetOf(col)
        return splitIndexes.sumOf { line ->
            (1..line.lastIndex)
                .filter { it in splitterColumns && line[it] == '^' } // keeps only locations with a splitter
                .onEach { i ->
                    splitterColumns.remove(i)  // removes the splitter column from tracked columns
                    splitterColumns.add(i - 1) // adds left to current tracked columns
                    splitterColumns.add(i + 1) // adds right to current tracked columns
                }.count()
        }
    }

    override fun part2(): Long {
        val particleTimelines = LongArray(quizInput.size).also { it[col] = 1 }

        splitIndexes.forEach { line ->
            // starting from index 1 as a splitter isn't be the first on the line
            (1..line.lastIndex)
                .filter { line[it] == '^' } // filters for only indexes with a splitter
                .onEach { i ->
                    particleTimelines[i - 1] += particleTimelines[i] // accumulates timelines on left of the splitter index
                    particleTimelines[i + 1] += particleTimelines[i] // accumulates timelines on right of the splitter index
                    particleTimelines[i] = 0  // removes accumulated timelines
                }
        }

        return particleTimelines.sum()
    }
}