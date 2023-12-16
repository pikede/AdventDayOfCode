package twentythree.daySixteen

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/daySixteen/file.txt"))

private fun main() {
    val beamSplitter = BeamSplitter(input)
    println(beamSplitter.getEnergizedCount(BeamDirection(Direction.Right, 0 to 0)))
    println(beamSplitter.getEnergizedRandomStart())
}

class BeamSplitter(val input: MutableList<String>) {
    fun getEnergizedCount(startingBeamDirection: BeamDirection): Int {
        val q = LinkedList<BeamDirection>()
        val energized = HashSet<Pair<Int, Int>>()
        q.offer(startingBeamDirection)
        energized.add(0 to 0)
        val visited = hashSetOf<BeamDirection>()
        while (q.isNotEmpty()) {
            val beam = q.poll()
            energized.add(beam.coordinates)
            for (next in movePosition(beam)) {
                if (isValid(next) && next !in visited) {
                    q.offer(next)
                    visited.add(next)
                }
            }
        }
        return energized.size
    }

    fun getEnergizedRandomStart(): Int {
        var max = 0
        val maxRows = input.size
        val maxColumns = input[0].length
        for (c in 0 until maxColumns) {
            max = maxOf(max, getEnergizedCount(BeamDirection(Direction.Down, 0 to c)))
            max = maxOf(max, getEnergizedCount(BeamDirection(Direction.Up, maxRows - 1 to c)))
        }
        for (r in 0 until maxRows) {
            max = maxOf(max, getEnergizedCount(BeamDirection(Direction.Right, r to 0)))
            max = maxOf(max, getEnergizedCount(BeamDirection(Direction.Left, r to maxColumns - 1)))
        }
        return max - 1
    }

    private fun movePosition(beam: BeamDirection): List<BeamDirection> {
        val nextDirections = mutableListOf<BeamDirection>()
        when {
            isDot(beam) -> {
                nextDirections += getNextDotDirection(beam)
            }
            isMirror(beam) -> {
                nextDirections += getNextMirrorDirection(beam)
            }
            isSplitter(beam) -> {
                nextDirections += getNextSplitterDirection(beam)
            }
        }
        return nextDirections
    }

    private fun isDot(beam: BeamDirection): Boolean {
        val (row, col) = beam.coordinates
        return input[row][col] == '.'
    }

    private fun getNextDotDirection(beam: BeamDirection): BeamDirection {
        val (row, column) = beam.coordinates
        return when (beam.direction) {
            Direction.Right -> beam.copy(coordinates = row to column + 1)
            Direction.Left -> beam.copy(coordinates = row to column - 1)
            Direction.Up -> beam.copy(coordinates = row - 1 to column)
            Direction.Down -> beam.copy(coordinates = row + 1 to column)
        }
    }

    private fun isMirror(beam: BeamDirection): Boolean {
        val (row, col) = beam.coordinates
        return input[row][col] == '/' || input[row][col] == '\\'
    }

    private fun getNextMirrorDirection(beam: BeamDirection): BeamDirection {
        val (row, column) = beam.coordinates
        return when (input[row][column] == '/') {
            true -> when (beam.direction) {
                Direction.Right -> BeamDirection(direction = Direction.Up, coordinates = row - 1 to column)
                Direction.Left -> BeamDirection(direction = Direction.Down, coordinates = row + 1 to column)
                Direction.Up -> BeamDirection(direction = Direction.Right, coordinates = row to column + 1)
                Direction.Down -> BeamDirection(direction = Direction.Left, coordinates = row to column - 1)
            }
            else -> {
                when (beam.direction) {
                    Direction.Right -> BeamDirection(direction = Direction.Down, coordinates = row + 1 to column)
                    Direction.Left -> BeamDirection(direction = Direction.Up, coordinates = row - 1 to column)
                    Direction.Up -> BeamDirection(direction = Direction.Left, coordinates = row to column - 1)
                    Direction.Down -> BeamDirection(direction = Direction.Right, coordinates = row to column + 1)
                }
            }
        }
    }

    private fun isSplitter(beam: BeamDirection): Boolean {
        val (row, column) = beam.coordinates
        return input[row][column] == '-' || input[row][column] == '|'
    }

    private fun getNextSplitterDirection(beam: BeamDirection): ArrayList<BeamDirection> {
        val directions = ArrayList<BeamDirection>()
        val (row, column) = beam.coordinates
        when {
            input[row][column] == '-' -> {
                when (beam.direction) {
                    Direction.Left, Direction.Right -> directions += getNextDotDirection(beam)
                    Direction.Down, Direction.Up -> {
                        directions += BeamDirection(direction = Direction.Right, coordinates = row to column + 1)
                        directions += BeamDirection(direction = Direction.Left, coordinates = row to column - 1)
                    }
                }
            }
            else -> {
                when (beam.direction) {
                    Direction.Left, Direction.Right -> {
                        directions += BeamDirection(direction = Direction.Down, coordinates = row + 1 to column)
                        directions += BeamDirection(direction = Direction.Up, coordinates = row - 1 to column)
                    }
                    Direction.Down, Direction.Up -> directions += getNextDotDirection(beam)
                }
            }
        }
        return directions
    }

    private fun isValid(beam: BeamDirection): Boolean {
        val (row, column) = beam.coordinates
        return row in input.indices && column in input[row].indices
    }
}

data class BeamDirection(val direction: Direction, val coordinates: Pair<Int, Int>)

enum class Direction {
    Up,
    Down,
    Left,
    Right
}