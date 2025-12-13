package twenty5.day12

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput: List<String> = readInput("twenty5/day12/file")

private fun main() {
    println(Day12.part1())  // 534
    println(Day12.part2())
}

private object Day12 : AOCPuzzle {
    val shapes = quizInput.chunked(5).take(6).map {
        val shape = it.drop(1).dropLast(1) // drops 0: and empty space
        Shape(shape)
    }

    val regions = quizInput.filter { it.any { it == 'x' } }.map {
        val (size, qty) = it.split(":")
        val (x, y) = size.split("x").map { it.toInt() }
        val quantity = qty.split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
        Regions(x, y, quantity)
    }

    override fun part1(): Any {
        return regions.count { region ->
            var spaceLeft = region.area
            region.quantities.forEachIndexed { index, i ->
                if (i != 0) {
                    val spaceConsumed = shapes[index].spaceConsumed * i
                    spaceLeft -= spaceConsumed
                }
            }
            spaceLeft >= 0
        }
    }

    override fun part2(): Any {
        return 0
    }

    data class Regions(val width: Int, val length: Int, val quantities: List<Int>) {
        val area = width * length
    }

    data class Shape(val shape: List<String>) {
        val spaceConsumed = shape.sumOf { line -> line.count { it == '#' } }
    }
}

