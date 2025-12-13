package twenty5.day9

import AOCPuzzle
import org.aoc.utils.readInput
import utils.Point2D
import utils.maxOf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val quizInput = readInput("twenty5/day9/file")

private fun main() {
    println(Day9.part1()) // 4776487744
    println(Day9.part2()) // 1560299548
}

private object Day9 : AOCPuzzle {

    private val points = quizInput.map {
        it.split(',').map { it.toInt() }.let {
            val (x, y) = it
            Point2D(x, y)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    val cornerPairs = buildList {
        (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                add(points[i] to points[j])
            }
        }
    }

    override fun part1(): Any {
        var max = 0L
        (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                val points = (points[i] to points[j])
                max = maxOf(points.area(), max)
            }
        }
        return max
    }

    private val area: Pair<Point2D, Point2D>.() -> Long = {
        val (point1, point2) = this
        val xDistance = abs(point1.x - point2.x) + 1
        val yDistance = abs(point1.y - point2.y) + 1
        xDistance.toLong() * yDistance.toLong()
    }

    override fun part2(): Any {
        fun Pair<Point2D, Point2D>.toSortedEdgePoints(): Pair<Point2D, Point2D> {
            val (p1, p2) = this
            val point1 = Point2D(min(p1.x, p2.x), min(p1.y, p2.y))
            val point2 = Point2D(max(p1.x, p2.x), max(p1.y, p2.y))
            return point1 to point2
        }

        val polygonEdges = (points + points.first())
            .zipWithNext()
            .map { it.toSortedEdgePoints() }

        return cornerPairs.map { it.toSortedEdgePoints() }.filter { (p1, p2) ->
            polygonEdges.none { (e1, e2) ->
                p1.x < e2.x && p2.x > e1.x && p1.y < e2.y && p2.y > e1.y
            }
        }.maxOf { it.area() }
    }
}