package twentythree.dayEighteen

import util.Move
import util.Point2D
import util.isValid
import util.printGrid
import java.lang.Math.abs
import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEighteen/file.txt"))

fun main() {
    val lavaCalculator = LavaCalculator(input)
    println(lavaCalculator.getFilledSize())
}

class LavaCalculator(val input: MutableList<String>) {
    private val coordinates = ArrayList<LagoonCoordinates>()
    private lateinit var grid: Grid

    init {
        parseInput()
        addEdges()
    }

    private fun addEdges() {
        grid = Grid()
        for (i in coordinates) {
            grid.move(i.direction, quantity = i.quantity)
        }
        grid.addEdges()
    }

    fun getFilledSize(): Int {
        grid.fillGrid()
//        grid.printGrid()
//        return grid.getArea()
        return 0
    }

    private fun parseInput() {
        for (i in input) {
            val lagoon = i.split(" ")
            val direction = lagoon[0][0]
            val quantity = Integer.parseInt(lagoon[1])
            val colorCode = lagoon[2]
            val lagoonCoordinates = LagoonCoordinates(direction, quantity, colorCode)
            coordinates.add(lagoonCoordinates)
        }
    }
}

data class LagoonCoordinates(val direction: Char, val quantity: Int, val colorCode: String)

private class Grid {
    lateinit var grid: Array<CharArray>
    val coordinates = arrayListOf<Pair<Point2D, Move?>>(Point2D(0, 0) to null)

    fun move(direction: Char, quantity: Int) {
        var start = coordinates.last().first.copy()

        val move = when (direction) {
            'R' -> Move.right
            'L' -> Move.left
            'U' -> Move.up
            else -> Move.down
        }
        repeat(quantity) {
            start = start.applyMove(move)
        }
        coordinates.add(start.copy() to move)
    }

    fun addEdges() {
        initializeGrid()
        val rows = grid.size
        val columns = grid[0].size
        for (index in 1..coordinates.lastIndex) {
            var (x1, y1) = coordinates[index - 1].first
            var (x2, y2) = coordinates[index].first
            if (x1 < 0) {
                x1 += columns
            }
            if (x2 < 0) {
                x2 += columns
            }
            if (y1 < 0) {
                y1 += rows
            }
            if (y2 < 0) {
                y2 += rows
            }
            val yStart = minOf(y1, y2)
            val yEnd = maxOf(y1, y2)
            val xStart = minOf(x1, x2)
            val xEnd = maxOf(x1, x2)

            if (y1 == y2) {
                fillColumns(yStart, xStart, xEnd)
            } else {
                fillRows(yStart, yEnd, xStart)
            }
        }
        val points = coordinates.map { it.first.y to it.first.x }
        println("area is ${area(points)}")
    }

    private fun fillColumns(row: Int, startColumn: Int, endColumn: Int) {
        for (c in startColumn..endColumn) {
            grid[row][c] = '#'
        }
    }

    private fun fillRows(startRow: Int, endRow: Int, column: Int) {
        for (r in startRow..endRow) {
            grid[r][column] = '#'
        }
    }

    private fun initializeGrid() {
        val maxX = coordinates.maxBy { it.first.x }?.first?.x ?: 0
        val minX = coordinates.minBy { it.first.x }?.first?.x ?: 0
        val maxY = coordinates.maxBy { it.first.y }?.first?.y ?: 0
        val minY = coordinates.minBy { it.first.y }?.first?.y ?: 0
        val y = abs(minY) + maxY + 1
        val x = abs(minX) + maxX + 1
        println(x)
        println(y)
        grid = Array(y) { CharArray(x) { '.' } }
    }

    fun fillGrid() {
        for (i in 1..coordinates.lastIndex) {
            val start = coordinates[i - 1]
            val end = coordinates[i]
            val move = when (start.second) {
                Move.right -> Move.down
                Move.up -> Move.right
                Move.down -> Move.left
                Move.left -> Move.up
                else -> {
                    Move.down
                }
            }
            println(start)
            floodFill(start, end, move)
        }
        printGrid()
    }

    private fun floodFill(start: Pair<Point2D, Move?>, end: Pair<Point2D, Move?>, fillMove: Move) {
        var tempStart = start.first
        while (isValid(tempStart.y, tempStart.x, grid) && tempStart != end.first) {
            tempStart = tempStart.applyMove(start.second ?: Move.right)
            fill(tempStart.copy(), fillMove)
        }
    }

    private fun fill(current: Point2D, move: Move) {
        var start = current
        start.applyMove(move)
        while (!isBorder(start) && isValid(start.y, start.x, grid) && start.valueOf(grid) != '#') {
            grid[start.y][start.x] = '#'
            start = start.applyMove(move)
            println("starts $start")
        }
    }

    fun isBorder(start: Point2D) = coordinates.any { it.first == start }

    fun printGrid() {
        grid.printGrid()
    }

    fun getArea(): Int {
        return grid.fold(0) { acc, currentRow ->
            acc + currentRow.count { it == '#' }
        }
    }

    private fun area(points: List<Pair<Int, Int>>): Long {
        var sum = 0L
        for (i in points.indices) {
            val j = if (i + 1 < points.size) i + 1 else 0
            sum += points[j].first.toLong() * points[i].second.toLong() -
                    points[i].first.toLong() * points[j].second.toLong()
        }
        return abs(sum / 2)
    }
}

// x -> 493
// y -> 334
// 121966
// 72413  // too high
// 70146  // not tried
// 55798 too low
// 90719 too high
// 230888
// 793235
// 793418
// 5165654
// 5870648
