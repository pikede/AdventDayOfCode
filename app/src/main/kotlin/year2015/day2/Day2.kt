package year2015.day2

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day2/file").toMutableList()

fun main() {
    val solution = Day2Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day2Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
    private val boxesDimensions by lazy {
        puzzleInput.map { dimension ->
            dimension.split("x").map { it.toInt() }
        }
    }

    override fun part1(): Any {
        return boxesDimensions.fold(0) { totalSurfaceArea, currentBox ->
            val (l, w, h) = currentBox
            val areaOfSmallestSide = minOf(h * l, l * w, w * h)
            val currentSurfaceArea = areaOfSmallestSide + (2 * l * w) + (2 * w * h) + (2 * h * l)
            currentSurfaceArea + totalSurfaceArea
        }
    }

    override fun part2(): Any {
        return boxesDimensions.fold(0) { totalRibbonLength, currentBox ->
            val (l, w, h) = currentBox
            val boxVolume = l * w * h
            val smallestBoxPerimeter = minOf(2 * (l + w), 2 * (l + h), 2 * (h + w))
            val currentRibbonLength = boxVolume + smallestBoxPerimeter
            currentRibbonLength + totalRibbonLength
        }
    }
}
