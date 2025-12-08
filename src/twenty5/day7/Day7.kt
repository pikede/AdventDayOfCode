package twenty5.day7

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day7/file.txt"))

private fun main() {
    println(Day7.part1())   // 1678
    println(Day7.part2())   // 357525737893560
}

@OptIn(ExperimentalStdlibApi::class)
private object Day7 : AOCPuzzle {
    private val col = quizInput.first { it.contains('S') }.indexOf('S')
    // only lines on the even indexes can have a splitter, these are the important lines
    private val splitIndexes = quizInput.filterIndexed { index, _ -> index % 2 == 0 }

    override fun part1(): Int {
        val splitterColumns = mutableSetOf(col)
        return splitIndexes.sumBy { line ->
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
            for (i in 1..line.lastIndex)
                if (line[i] == '^') { // if splitter
                    particleTimelines[i - 1] += particleTimelines[i] // add possible timelines to timeline on left
                    particleTimelines[i + 1] += particleTimelines[i] // add possible timelines to timeline on right
                    particleTimelines[i] = 0  // remove accumulated timelines
                }
        }

        return particleTimelines.sum()
    }
}