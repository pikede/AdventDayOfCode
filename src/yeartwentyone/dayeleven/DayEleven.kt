package yeartwentyone.dayeleven

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/dayeleven/file.txt")) as ArrayList<String>
    val solution = Octopus(input)
    println(solution.partOne())  //  1741
    println(Octopus(input).partTwo())  //  440
}

class Octopus(val input: List<String>) {
    private var matrix: Array<Array<Int>> = Array(input.size) { Array(input[0].length) { 0 } }
    private val ROWS = arrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
    private val COLS = arrayOf(-1, 0, 1, -1, 1, -1, 0, 1)

    init {
        for (row in input.indices) {
            for (col in input[row].indices) {
                matrix[row][col] = input[row][col] - '0'
            }
        }
    }

    fun partOne(): Int {
        var flashes = 0
        val steps = 100

        for (i in 0 until steps) {
            val allFlashed = ArrayList<Pair<Int, Int>>()

            for (row in matrix.indices) {
                for (col in matrix[row].indices) {
                    matrix[row][col]++
                    if (matrix[row][col] > 9) {
                        val pair = Pair(row, col)
                        allFlashed.add(pair)
                        matrix[row][col] = 0
                        flashes++
                    }
                }
            }

            for (k in allFlashed) {
                flashes += flashLocation(k.first, k.second)
            }
        }

        return flashes
    }

    fun partTwo(): Int {
        var steps = 0

        while (!allFlashed()) {
            val flashedPairs = ArrayList<Pair<Int, Int>>()
            for (row in matrix.indices) {
                for (col in matrix[row].indices) {
                    matrix[row][col]++

                    if (matrix[row][col] > 9) {
                        val pair = Pair(row, col)
                        flashedPairs.add(pair)
                        matrix[row][col] = 0
                    }
                }
            }

            for (k in flashedPairs) {
                // flashLocation output isn't needed
                flashLocation(k.first, k.second)
            }

            if (allFlashed()) {
                // handles 0-index, off-1 issue
                return steps + 1
            }
            steps++
        }

        return steps
    }

    private fun flashLocation(row: Int, col: Int): Int {
        var cnt = 0

        for (i in ROWS.indices) {
            val x = ROWS[i] + row
            val y = COLS[i] + col
            if (isValidLocation(x, y) && matrix[x][y] != 0) {
                matrix[x][y]++
                if (matrix[x][y] > 9) {
                    matrix[x][y] = 0
                    cnt++
                    cnt += flashLocation(x, y)
                }
            }
        }

        return cnt
    }

    private fun isValidLocation(x: Int, y: Int) = x in matrix.indices && y in matrix[x].indices

    private fun allFlashed(): Boolean {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (matrix[row][col] != 0) {
                    return false
                }
            }
        }

        return true
    }

    private fun printMatrix() {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                print("${matrix[row][col]}, ")
            }
            println()
        }
        println()
    }
}