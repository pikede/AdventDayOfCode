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
    println(maze.getLengthEnclosedInMainLoop())
}

class PipeMaze(private val mazeInput: MutableList<String>) {
    private val rows = mazeInput.size
    private val columns = mazeInput[0].length
    private val maze = Array(rows) { IntArray(columns) }
    private val loop = ArrayList<Pair<Int, Int>>()
    private val insideTheLoop = HashSet<Pair<Int, Int>>()

    fun getFarthestLoopLength(): Int {
        val startingPoint = getStartingPoint()
        val q = LinkedList<Pair<Int, Int>>()
        val visited = HashSet<Pair<Int, Int>>()
//        q.offer(startingPoint.copy(second = startingPoint.second + 1))
//        q.offer(startingPoint.copy(first = startingPoint.first + 1))

        q.offer(startingPoint.copy(first = startingPoint.first - 1))
        println(startingPoint)
//        q.offer(startingPoint.copy(second = startingPoint.second - 1))
        loop.add(startingPoint)
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
        println(steps)
        return ceil(steps.toDouble() / 2.toDouble()).toInt()
    }

    fun getLengthEnclosedInMainLoop(): Int {
        paintRows()
        paintColumns()
        for (i in 1 until loop.size) {
            val point1 = loop[i - 1]
            val point2 = loop[i]
            when {
                isRightOf(point1, point2) -> paintInside(point2.copy(first = point2.first + 1)) // down
                isLeftOf(point1, point2) -> paintInside(point2.copy(first = point2.first - 1)) // up
                isAboveOf(point1, point2) -> paintInside(point2.copy(second = point2.second + 1)) // right
                isBelowOf(point1, point2) -> paintInside(point2.copy(second = point2.second - 1)) // left
            }
        }
        println(insideTheLoop)
        printMazeForEnclosedLoop()
        return insideTheLoop.size
    }

    private fun paintInside(point: Pair<Int, Int>) {
        if (!isValid(point) || !point.isZero() || point in insideTheLoop) {
            return
        }
        insideTheLoop.add(point)
//        maze[point.first][point.second] = 9
        paintInside(point.copy(first = point.first - 1))
        paintInside(point.copy(first = point.first + 1))
        paintInside(point.copy(second = point.second - 1))
        paintInside(point.copy(second = point.second + 1))
    }

    private fun isAboveOf(point1: Pair<Int, Int>, point2: Pair<Int, Int>): Boolean {
        return (point1.first - 1) == point2.first && point1.second == point2.second
    }

    private fun isBelowOf(point1: Pair<Int, Int>, point2: Pair<Int, Int>): Boolean {
        return (point1.first + 1) == point2.first && point1.second == point2.second
    }

    private fun isRightOf(point1: Pair<Int, Int>, point2: Pair<Int, Int>): Boolean {
        return point1.first == point2.first && (point1.second + 1) == point2.second
    }

    private fun isLeftOf(point1: Pair<Int, Int>, point2: Pair<Int, Int>): Boolean {
        return point1.first == point2.first && (point1.second - 1) == point2.second
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
                    r to c in insideTheLoop -> print("$")
                    r to c in loop -> print("*")
                    else -> print("${mazeInput[r][c]}")
                }
            }
            println()
        }
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

}
//739 --> too high

// 431 too low

// 452 | 453
// 277