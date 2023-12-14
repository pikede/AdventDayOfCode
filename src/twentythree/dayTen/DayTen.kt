package twentythree.dayTen

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.ceil

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTen/file.txt"))

fun main() {
    val maze = PipeMaze(input)
    println(maze.getFarthestLoopLength())
    // commented out part 2
}

class PipeMaze(private val mazeInput: MutableList<String>) {
    private val rows = mazeInput.size
    private val columns = mazeInput[0].length
    private val maze = Array(rows) { IntArray(columns) }
    private val loop = ArrayList<Pair<Int, Int>>()

    fun getFarthestLoopLength(): Int {
        val startingPoint = getStartingPoint()
        val q = LinkedList<Pair<Int, Int>>()
        val visited = HashSet<Pair<Int, Int>>()
//        q.offer(startingPoint.copy(second = startingPoint.second + 1))
//        q.offer(startingPoint.copy(first = startingPoint.first + 1))

//        q.offer(startingPoint.copy(first = startingPoint.first - 1))
        q.offer(startingPoint.copy(second = startingPoint.second - 1))
//        loop.add(startingPoint)
        var steps = 1
        while (q.isNotEmpty()) {
            repeat(q.size) {
                val node = q.poll()
                loop.add(node)
                visited.add(node)
                maze[node.first][node.second] = steps
                for (next in getNextDirection(node.first, node.second)) {
                    if (isValid(next) && next !in visited && next != startingPoint) {
                        q.offer(next)
                        visited.add(next)
                    }
                }
            }
            if (q.isEmpty()) {
                break
            }
            steps++
        }
        return ceil(steps.toDouble() / 2.toDouble()).toInt()
    }

    private fun isValid(point: Pair<Int, Int>) =
        point.first in mazeInput.indices && point.second in mazeInput[point.first].indices

    private fun getStartingPoint(): Pair<Int, Int> {
        for (r in mazeInput.indices) {
            for (c in mazeInput[r].indices) {
                if (mazeInput[r][c] == 'S') {
                    return r to c
                }
            }
        }
        return -1 to -1
    }

    private fun getNextDirection(row: Int, col: Int): ArrayList<Pair<Int, Int>> {
        val directions = ArrayList<Pair<Int, Int>>()
        when (mazeInput[row][col]) {
            '|' -> {
                directions.add(row + 1 to col)
                directions.add(row - 1 to col)
            }
            '-' -> {
                directions.add(row to col + 1)
                directions.add(row to col - 1)
            }
            'L' -> {
                directions.add(row - 1 to col)
                directions.add(row to col + 1)
            }
            'J' -> {
                directions.add(row - 1 to col)
                directions.add(row to col - 1)
            }
            '7' -> {
                directions.add(row + 1 to col)
                directions.add(row to col - 1)
            }
            'F' -> {
                directions.add(row + 1 to col)
                directions.add(row to col + 1)
            }
            else -> {
                // S || .
                row to col
            }
        }
        return directions
    }
/*
    fun part2(input: List<String>): Int {
        val (c, r) = getStartingPoint()
        var cursor1 = Cursor(x = c, y = r, from = Direction.Top)
        var cursor2 = Cursor(x = c, y = r, from = Direction.Left)
        val map = input.map { it.toCharArray() }

        do {
            map[cursor1.y][cursor1.x] = '@'
            map[cursor2.y][cursor2.x] = '@'
            cursor1 = takeStep(input[cursor1.y][cursor1.x], cursor1)
            cursor2 = takeStep(input[cursor2.y][cursor2.x], cursor2)
        } while ((cursor1.x != cursor2.x) || (cursor1.y != cursor2.y))

        map.forEachIndexed { y, line ->
            var parity = false

            line.forEachIndexed { x, tile ->
                if (tile == '@') {
                    if (
                        (input[y][x] == '|')
                        || (input[y][x] == 'F')
                        || (input[y][x] == '7')
                    ) parity = !parity
                } else {
                    if (parity) map[y][x] = 'I' else map[y][x] = 'O'
                }
            }
        }

        return map.sumBy { it.filter { it == 'I' }.count() }
    }

    private fun takeStep(tile: Char, cursor: Cursor): Cursor {
        val from = cursor.from
        val x = cursor.x
        val y = cursor.y

        return when (tile) {
            '|' -> {
                if (from == Direction.Top) Cursor(x, y + 1, Direction.Top)
                else Cursor(x, y - 1, Direction.Bottom)
            }
            '-' -> {
                if (from == Direction.Left) Cursor(x + 1, y, Direction.Left)
                else Cursor(x - 1, y, Direction.Right)
            }
            'L' -> {
                if (from == Direction.Top) Cursor(x + 1, y, Direction.Left)
                else Cursor(x, y - 1, Direction.Bottom)
            }
            'J', 'S' -> { // Kinda a cheat, found S to be a J tile by looking at input data
                if (from == Direction.Top) Cursor(x - 1, y, Direction.Right)
                else Cursor(x, y - 1, Direction.Bottom)
            }
            '7' -> {
                if (from == Direction.Bottom) Cursor(x - 1, y, Direction.Right)
                else Cursor(x, y + 1, Direction.Top)
            }
            'F' -> {
                if (from == Direction.Bottom) Cursor(x + 1, y, Direction.Left)
                else Cursor(x, y + 1, Direction.Top)
            }
            else -> {
                error("Encountered invalid tile $tile")
            }
        }
    }
    */
}

enum class Direction {
    Bottom,
    Top,
    Left,
    Right
}

data class Cursor(var x: Int, var y: Int, var from: Direction)