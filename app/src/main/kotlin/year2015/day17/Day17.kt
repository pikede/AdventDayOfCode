package year2015.day17

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput= readInput("src/year2015/day17/file")
private const val CONTAINER_LIMIT = 150

private fun main() {
    val solution = Day17Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day17Solution : AOCPuzzle {
    val combinations = ArrayList<ArrayList<Int>>()

    @OptIn(ExperimentalStdlibApi::class)
    val containers = buildList {
        quizInput.forEach {
            add(it.toInt())
        }
    }

    init {
        buildCombinations(combinations, arrayListOf())
    }

    private fun buildCombinations(combinations: ArrayList<ArrayList<Int>>, level: ArrayList<Int>, start: Int = 0) {
        if (level.sum() == CONTAINER_LIMIT) {
            combinations.add(ArrayList(level))
            return
        }

        for (index in start..containers.lastIndex) {
            val i = containers[index]
            if (i + level.sum() <= CONTAINER_LIMIT) {
                level += i
                buildCombinations(combinations, level, index + 1)
                level.removeAt(level.lastIndex)
            }
        }
    }

    override fun part1(): Any {
        return combinations.size
    }

    override fun part2(): Any {
        val minContainers = combinations.minBy { it.size }?.size ?: 0
        return combinations.count { it.size == minContainers }
    }
}
