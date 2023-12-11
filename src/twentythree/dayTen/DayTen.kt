package twentythree.dayTen

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTen/file.txt"))

fun main() {
    val maze = PipeMaze(input)
    println(maze.getFarthestLoopLength())
    println(maze.getLengthEnclosedInMainLoop())
}
/*
| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 */

class PipeMaze(private val mazeInput: MutableList<String>) {
    val rows = mazeInput.size
    private val columns = mazeInput[0].length
    private val maze = Array(rows) { IntArray(columns) }
    val loop = HashSet<Pair<Int, Int>>()
    val invalid = HashSet<Pair<Int,Int>>()

    fun getFarthestLoopLength(): Int {
        val startingPoint = getStartingPoint()
        val q = LinkedList<Pair<Int, Int>>()
        val visited = HashSet<Pair<Int, Int>>()
        // mine left and up
        q.offer(startingPoint.copy(first = startingPoint.first - 1))
        q.offer(startingPoint.copy(second = startingPoint.second - 1))
//        visited.add(startingPoint)
        var steps = 1
        while (q.isNotEmpty()) {
            repeat(q.size) {
                val node = q.poll()
                loop.add(node)
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
        return steps
    }

    fun getLengthEnclosedInMainLoop(): Int {
        paintRows()
        paintColumns()
        val temp = HashSet<Pair<Int, Int>>()
        for (r in maze.indices) {
            for (c in maze[r].indices) {
                if ( (r to c).isZero() && r to c != getStartingPoint()) {
                    temp.add(r to c)
                }
            }
        }
        var count = 0
        for (i in temp) {
            if (isEnclosed(i))
                count++
            else {
                invalid.add(i)
            }

        }
        printMazeForEnclosedLoop()
        return count
    }

    private fun isEnclosed(pair: Pair<Int, Int>): Boolean {
        val up = pair.copy(first = pair.first - 1)
        var count = 0
        while (isValid(up)) {
            if (up in loop || up.isZero()) {
                break
            } else {
                return false
            }
        }

        val down = pair.copy(first = pair.first + 1)
        while (isValid(down)) {
            if (down in loop || down.isZero()) {
                break
            } else {
                return false
            }
        }

        val left = pair.copy(second = pair.second - 1)
        while (isValid(left)) {
            if (left in loop || left.isZero()) {
                break
            } else {
                return false
            }
        }

        val right = pair.copy(second = pair.second + 1)
        while (isValid(right)) {
            if (left in loop || left.isZero()) {
                break
            } else {
                return false
            }
        }
        return true
    }

    private fun paintRows() {
        for (r in 0 until rows) {
            if (maze[r][0] == 0) {
                paint(r to 0)
            }
            if (maze[r][columns - 1] == 0) {
                paint(r to columns - 1)
            }
        }
    }

    private fun paintColumns() {
        for (c in 0 until columns) {
            if (maze[0][c] == 0) {
                paint(0 to c)
            }
            if (maze[rows - 1][c] == 0) {
                paint(rows - 1 to c)
            }
        }
    }

    private fun paint(point: Pair<Int, Int>) {
        if (!isValid(point) || !point.isZero()) {
            return
        }
        maze[point.first][point.second] = -1
        paint(point.copy(first = point.first - 1))
        paint(point.copy(first = point.first + 1))
        paint(point.copy(second = point.second - 1))
        paint(point.copy(second = point.second + 1))
    }

    private fun isValid(point: Pair<Int, Int>) =
        point.first in mazeInput.indices && point.second in mazeInput[point.first].indices

    private fun Pair<Int, Int>.isZero() = maze[first][second] == 0

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

    private fun printMazeForEnclosedLoop() {
        for (r in maze.indices) {
            for (c in maze[r].indices) {
                when {
                    r to c == getStartingPoint() -> print("S")
                    r to c in invalid -> print("*")
                    maze[r][c] == 0 -> print("$")
                    else -> print("${mazeInput[r][c]}")
                }
            }
            println()
        }
    }
}
//739 --> too high

// 431 too low