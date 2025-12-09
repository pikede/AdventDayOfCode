package twenty5.day9

import AOCPuzzle
import utils.Point2D
import utils.Point3D
import utils.runningFold
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day9/file.txt"))

private fun main() {
    println(Day9.part1()) // 4776487744
    println(Day9.part2())
}

private object Day9 : AOCPuzzle {

    override fun part1(): Any {
        val points = quizInput.map {
            it.split(',').map { it.toInt() }.let {
                val (x, y) = it
                Point2D(x, y)
            }
        }
        var max = 0L
        (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                val xDistance = abs(points[i].x - points[j].x) + 1
                val yDistance = abs(points[i].y - points[j].y) + 1
                max = maxOf(xDistance.toLong() * yDistance.toLong(), max)
            }
        }
        return max
    }

    override fun part2(): Any {
        return 0
    }
}