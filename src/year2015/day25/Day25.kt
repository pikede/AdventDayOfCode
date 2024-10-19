package year2015.day25

import AOCPuzzle

private fun main() {
    val day25 = Day25Solution
    println(day25.part1())
//    println(day25.part2())
}

private object Day25Solution : AOCPuzzle {
    val grid = Array(10000) { LongArray(10000) }

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

//        grid.forEach { array ->
//            array.forEach {
//                if (it != 0L) {
//                    print("$it, ")
//                }
//            }
//            println()
//        }
        val row = 2981
        val column = 3075
        return grid[row - 1][column - 1]
    }

    override fun part2(): Any {
        return 0
    }
}
