package twentythree.dayFourteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFourteen/file.txt"))

fun main() {
    val rockTilt = RockTilt(input)
    println(rockTilt.getRockLoadSum())
}

class RockTilt(private val input: MutableList<String>) {
    private val rows = input.size
    private val columns = input[0].length
    private val rocks = Array(rows) { CharArray(columns) }

    init {
        parseInput()
        tiltRocks()
    }

    fun getRockLoadSum(): Int {
        var totalSum = 0
        for (index in rocks.indices) {
            val count = rocks[index].count { it == 'O' }
            val currentSum = (count * (rocks.size - index))
            totalSum += currentSum
        }
        return totalSum
    }

    private fun parseInput() {
        for (r in input.indices) {
            for (c in input[r].indices) {
                rocks[r][c] = input[r][c]
            }
        }
    }

    private fun tiltRocks() {
        for (r in 1..rocks.lastIndex) {
            for (c in rocks.indices) {
                if (rocks[r][c] == 'O') {
                    moveNorth(r, c)
                }
            }
        }
    }

    private fun moveNorth(row: Int, column: Int) {
        var end = row - 1
        if (!isValid(end, column) || rocks[end][column] != '.') {
            return
        }

        while (end - 1 in rocks.indices && rocks[end - 1][column] == '.') {
            end--
        }

        val temp = rocks[end][column]
        rocks[end][column] = rocks[row][column]
        rocks[row][column] = temp
    }

    private fun isValid(row: Int, column: Int) = row in rocks.indices && column in rocks[row].indices

    private fun printRocks() {
        for (r in rocks.indices) {
            for (c in rocks[r].indices) {
                print("${rocks[r][c]}")
            }
            println()
        }
    }
}