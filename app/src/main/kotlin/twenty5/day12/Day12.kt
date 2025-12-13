package twenty5.day12

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput: List<String> = readInput("twenty5/day12/file")

private fun main() {
    println(Day12.part1())  // 534
    println(Day12.part2())
}

private object Day12 : AOCPuzzle {
    private val shapes = quizInput
        .chunked(5) // takes every group of 5 lines
        .take(6) // there's only 0..5 the rest is the regions
        .map {
            val shape = it.drop(1).dropLast(1) // drops 0: and empty space
            Shape(shape)
        }

    private val regions = quizInput
        .filter { line -> line.any { it == 'x' } } // looks for only lines that are have the multiplier sign
        .map {
            val (size, qty) = it.split(":")
            val (width, length) = size.split("x").map { widthLength -> widthLength.toInt() }
            val validQuantity = qty.split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
            Regions(width, length, validQuantity)
        }

    override fun part1(): Any {
        return regions.count { region ->
            var spaceLeft = region.area
            region.quantities.forEachIndexed { index, shapesCount ->
                if (shapesCount != 0) {
                    val spaceConsumed = shapes[index].spaceConsumed * shapesCount
                    spaceLeft -= spaceConsumed
                }
            }
            spaceLeft >= 0
        }
    }

    override fun part2(): Any {
        return 0
    }

    private data class Regions(val width: Int, val length: Int, val quantities: List<Int>) {
        val area = width * length
    }

    private data class Shape(val shape: List<String>) {
        val spaceConsumed = shape.sumOf { line -> line.count { it == '#' } }
    }
}

