package twentythree.dayFourteen

import utils.printGrid
import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFourteen/file.txt"))

fun main() {
    val rockTilt = RockTilt(input)
    println(rockTilt.getRockLoadSumAfterOneCycle())
    println(rockTilt.getRockLoadSumAfterBillionCycles())
}

class RockTilt(private val input: MutableList<String>) {
    private val rows = input.size
    private val columns = input[0].length
    private val rocks = Array(rows) { CharArray(columns) }

    init {
        parseInput()
    }

    private fun parseInput() {
        for (r in input.indices) {
            for (c in input[r].indices) {
                rocks[r][c] = input[r][c]
            }
        }
        rocks.printGrid()
    }

    fun getRockLoadSumAfterOneCycle(): Int {
        val grid = tiltRocks(rocks.toList(), Direction.North)
        return getRockLoadSum(grid)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getRockLoadSumAfterBillionCycles(): Int {
        val cache = mutableMapOf<List<String>, Int>()
        var grid = rocks.toList()
        var cycle = 0
        var remainingCycles = 0
        val cycleLimit = 1000000000
        while (cycle < cycleLimit) {
            val key = grid.map { it.concatToString() }  // same as String(chararray)
            if (key in cache) {
                remainingCycles = cycle - cache[key]!!
                break
            }
            cache[key] = cycle
            grid = spin(grid)
            cycle++
        }
        if (remainingCycles > 0) {
            val remaining = (cycleLimit - cycle) % remainingCycles
            repeat(remaining) {
                grid = spin(grid)
            }
        }
        return getRockLoadSum(grid)
    }

    private fun spin(currentGrid: List<CharArray>): List<CharArray> {
        val northGrid = tiltRocks(grid = currentGrid, direction = Direction.North)
        val westGrid = tiltRocks(grid = northGrid, direction = Direction.West)
        val southGrid = tiltRocks(grid = westGrid, direction = Direction.South)
        return tiltRocks(grid = southGrid, direction = Direction.East)
    }

    private fun tiltRocks(grid: List<CharArray>, direction: Direction): List<CharArray> {
        val rows = grid.size
        val columns = grid[0].size
        val afterTilt = Array(rows) { CharArray(columns) { '.' } }
        when (direction) {
            Direction.North -> {
                for (c in 0 until columns) {
                    var newRowIndex = 0
                    for (r in 0 until rows) {
                        when {
                            grid[r][c] == 'O' -> {
                                afterTilt[newRowIndex++][c] = grid[r][c]
                            }
                            grid[r][c] == '#' -> {
                                afterTilt[r][c] = grid[r][c]
                                newRowIndex = r + 1
                            }
                        }
                    }
                }
            }
            Direction.West -> {
                for (r in 0 until rows) {
                    var newColumnIndex = 0
                    for (c in 0 until columns) {
                        when {
                            grid[r][c] == 'O' -> {
                                afterTilt[r][newColumnIndex++] = grid[r][c]
                            }
                            grid[r][c] == '#' -> {
                                afterTilt[r][c] = grid[r][c]
                                newColumnIndex = c + 1
                            }
                        }
                    }
                }
            }
            Direction.South -> {
                for (c in 0 until columns) {
                    var newRowIndex = rows - 1
                    for (r in rows-1 downTo 0) {
                        when {
                            grid[r][c] == 'O' -> {
                                afterTilt[newRowIndex--][c] = grid[r][c]
                            }
                            grid[r][c] == '#' -> {
                                afterTilt[r][c] = grid[r][c]
                                newRowIndex = r - 1
                            }
                        }
                    }
                }
            }
            Direction.East -> {
                for (r in 0 until rows) {
                    var newColumnIndex = columns - 1
                    for (c in columns - 1 downTo 0) {
                        when {
                            grid[r][c] == 'O' -> {
                                afterTilt[r][newColumnIndex--] = grid[r][c]
                            }
                            grid[r][c] == '#' -> {
                                afterTilt[r][c] = grid[r][c]
                                newColumnIndex = c - 1
                            }
                        }
                    }
                }
            }
        }
        return afterTilt.toList()
    }

    private fun getRockLoadSum(grid: List<CharArray>): Int {
        var totalSum = 0
        for (index in grid.indices) {
            val count = grid[index].count { it == 'O' }
            val currentSum = (count * (rocks.size - index))
            totalSum += currentSum
        }
        return totalSum
    }
}

enum class Direction {
    North,
    West,
    East,
    South;
}