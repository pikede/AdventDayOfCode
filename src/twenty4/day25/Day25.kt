package twenty4.day25

import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty4/day25/file.txt"))
val grids = mutableListOf<Grid>()
val locks = mutableListOf<Grid>()
val keys = mutableListOf<Grid>()
private fun main() {
    parse()
    println(part1())
}

private fun part1(): Int {
    var count = 0
    for (key in keys) {
        for (lock in locks) {
            if (isMatch(key, lock)) {
                count++
            }
        }
    }
    return count
}

private fun isMatch(key: Grid, lock: Grid): Boolean {
    for (index in key.heights.indices) {
        if (key.heights[index] + lock.heights[index] >= 6) {
            return false
        }
    }
    return true
}

private fun parse() {
    val grid = mutableListOf<String>()
    for (i in quizInput) {
        if (i.isEmpty()) {
            val isLock = grid[0].all { it == '#' } //&& !grid.last().all { it == '.' }
            val tempGrid = Grid(grid.toMutableList(), isLock)
            if (isLock) locks += tempGrid else keys += tempGrid
            grids.add(tempGrid)
            grid.clear()
            continue
        }
        grid.add(i)
    }
    val isLock = grid[0].all { it == '#' } //&& !grid.last().all { it == '.' }
    grids.add(Grid(grid.toMutableList(), isLock))
    val tempGrid = Grid(grid.toMutableList(), isLock)
    if (isLock) locks += tempGrid else keys += tempGrid
}
// lock top row == #
// key bottom rown == ##

data class Grid(val matrix: MutableList<String>, var isLock: Boolean) {
    val heights = MutableList(matrix[0].length) { 0 }

    init {
        for (r in matrix.indices) {
            for (c in matrix[r].indices) {
                if (isLock && r == 0) continue
                if (!isLock && r == matrix.lastIndex) continue
                heights[c] += if (matrix[r][c] == '#') 1 else 0
            }
        }
    }
}
