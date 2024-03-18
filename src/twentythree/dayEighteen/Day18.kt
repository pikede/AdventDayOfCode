package twentythree.dayEighteen

import utils.*
import java.lang.Math.abs
import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEighteen/file.txt"))

fun main() {
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Long {
    return shoelaceDig(input.map(::DigLine))
}

fun part2(input: List<String>): Long {
    return shoelaceDig(input.map(::DigLine).map { it.fixLine() })
}

data class DigLine(val direction: Compass, val steps: Int, val color: String) {

    fun fixLine(): DigLine {
        val direction = when (color.takeLast(1).toInt()) {
            0 -> Compass.East
            1 -> Compass.South
            2 -> Compass.West
            3 -> Compass.North
            else -> throw IllegalArgumentException("Bad direction")
        }
        val steps = color.dropLast(1).toInt(16)
        return DigLine(direction, steps, color)
    }
}

fun DigLine(line: String): DigLine {
    val (rawDirection, stepsChar, colorString) = line.split(" ")
    val direction = when (rawDirection) {
        "U" -> Compass.North
        "R" -> Compass.East
        "D" -> Compass.South
        "L" -> Compass.West
        else -> throw IllegalArgumentException("Bad direction")
    }
    val steps = stepsChar.toInt()
    val color = colorString.filter { it !in "(#)" }
    return DigLine(direction, steps, color)
}

fun shoelaceDig(digs: List<DigLine>): Long {
    var x = 0L
    var y = 0L

    // Add the first point to the back as well
    val shoelace = (digs + digs.first()).windowed(2) { (_, end) ->
        val (nextX, nextY) = when (end.direction) {
            Compass.North -> x to (y + end.steps)
            Compass.East -> (x + end.steps) to y
            Compass.South -> x to (y - end.steps)
            Compass.West -> (x - end.steps) to y
        }
        val determinant = (x * nextY) - (y * nextX)
        x = nextX
        y = nextY
        determinant
    }
        .sum()
        .let { abs(it / 2) }

    // Include the outline of the shape, off by 1 because of corners
    val outline = digs.sumBy { it.steps } / 2 + 1

    return shoelace + outline
}
