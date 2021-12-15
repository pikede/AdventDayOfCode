package yeartwentyone.daynine

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/daynine/file.txt"))
    val solution = SmokeBasin(input as ArrayList<String>)
    println(solution.partOne())   // 564
    println(solution.partTwo())   // 1038240
}

class SmokeBasin(val input: ArrayList<String>) {
    private val matrix = Array(input.size) { Array(input[0].length) { 0 } }
    private val visited = Array(input.size) { Array(input[0].length) { false } }

    private val ROWS = arrayOf(-1, 0, 0, 1)
    private val COLS = arrayOf(0, -1, 1, 0)
    private val lowPointsList = ArrayList<Pair<Int, Int>>()

    init {
        for ((row, elements) in input.withIndex()) {
            for (col in elements.indices) {
                matrix[row][col] = elements[col] - '0'
            }
        }
    }

    fun partOne(): Int {
        var riskLevel = 0

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (lowPoint(row, col, matrix[row][col])) {
                    lowPointsList.add(Pair(row, col))
                    riskLevel += (matrix[row][col] + 1)
                }
            }
        }

        return riskLevel
    }

    fun partTwo(): Int {
        var basinSizes = ArrayList<Int>()

        for (i in lowPointsList) {
            visited[i.first][i.second] = true
            val temp = ArrayList<Int>()
            temp.add(matrix[i.first][i.second])
            var basinSize = getBasinSize(i.first, i.second, temp)
            basinSizes.add(basinSize.size)
        }

        basinSizes.sort()
        val n = basinSizes.size
        return basinSizes[n - 1] * basinSizes[n - 2] * basinSizes[n - 3]
    }

    private fun getBasinSize(
        row: Int,
        col: Int,
        basin: ArrayList<Int>
    ): ArrayList<Int> {
        for (i in ROWS.indices) {
            val x = ROWS[i] + row
            val y = COLS[i] + col

            if (isValid(x, y) && !visited[x][y] && matrix[x][y] != 9) {
                visited[x][y] = true
                basin.add(matrix[x][y])
                getBasinSize(x, y, basin)
            }
        }

        return basin
    }

    private fun lowPoint(row: Int, col: Int, lowest: Int): Boolean {
        for (i in ROWS.indices) {
            val x = ROWS[i] + row
            val y = COLS[i] + col
            if (isValid(x, y) && matrix[x][y] <= lowest) {
                return false
            }
        }
        return true
    }

    private fun isValid(x: Int, col: Int) = x in matrix.indices && col in matrix[x].indices
}