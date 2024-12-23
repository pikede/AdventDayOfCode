package year2015.day25

import AOCPuzzle

private const val row = 2981
private const val column = 3075

private fun main() {
    val day25 = Day25Solution
    println(day25.part1())
    println(day25.part2())
}

private object Day25Solution : AOCPuzzle {
    val grid = Array(10000) { LongArray(10000) } // picked a much larger number for max row and max column

    override fun part1(): Any {
        var currentRow = 1
        var currentValue = 20151125L
        grid[0][0] = currentValue
        while (grid[2981][3075] == 0L) {
            for (c in 0..currentRow) {
                val nextValue = (currentValue * 252533L) % 33554393L
                currentValue = nextValue
                if (currentRow - c in grid.indices && c in grid[0].indices) {
                    grid[currentRow - c][c] = currentValue
                } else {
                    break
                }
            }
            currentRow++
        }
        return grid[row - 1][column - 1]
    }

    override fun part2() = 0 // no part 2
}
