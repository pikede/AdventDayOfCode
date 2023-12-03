package twentythree.daythree

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/daythree/file.txt"))

fun main() {
    val partChecker = PartChecker(input) // doesn't count space complexity for input
    println(partChecker.getPartSum()) // Time complexity: n * k * 8 -> nk | 1
    println(partChecker.getGearSum()) // // Time complexity: n * k * 8 -> nk | nk store visited locations
}

class PartChecker(val input: MutableList<String>) {
    private val matrix = Array(input.size) { CharArray(input[0].length) { '.' } }

    init {
        for (r in input.indices) {
            for (c in input[r].indices) {
                matrix[r][c] = input[r][c]
            }
        }
    }

    fun getPartSum(): Int {
        var totalPartsSum = 0
        for (rowIndex in input.indices) {
            val row = input[rowIndex]
            totalPartsSum += getParts(row, rowIndex)
        }
        return totalPartsSum
    }

    private fun getParts(row: String, rowIndex: Int): Int {
        var total = 0
        var start = 0
        while (start in row.indices) {
            if (row[start].isDigit()) {
                var end = start
                loop@ while (end + 1 in row.indices) {
                    when {
                        row[end + 1].isDigit() -> end++
                        else -> break@loop
                    }
                }
                total += getAdjacentNumber(rowIndex, start, end)
                start = end
            }
            start++
        }
        return total
    }

    private fun getAdjacentNumber(rowIndex: Int, startIndex: Int, endIndex: Int): Int {
        val isAdjacent = isNumberAdjacentToSign(rowIndex, startIndex, endIndex)
        return if (isAdjacent) {
            val currentRow = input[rowIndex]
            val candidate = currentRow.substring(startIndex, endIndex + 1)
            parseDigitFromString(candidate)
        } else {
            0
        }
    }

    private fun parseDigitFromString(word: String): Int {
        return Integer.parseInt(word)
    }

    private fun isNumberAdjacentToSign(rowIndex: Int, startColumn: Int, endColumn: Int): Boolean {
        if (isValidGearPart(rowIndex, startColumn - 1) || isValidGearPart(rowIndex, endColumn + 1)) {
            return true
        }
        val directions = listOf(1 to 0, -1 to 0, 1 to 1, -1 to 1, 1 to -1, -1 to -1)
        for (i in startColumn..endColumn) {
            for (direction in directions) {
                val row = direction.first + rowIndex
                val column = direction.second + i
                if (isValidGearPart(row, column)) {
                    return true
                }
            }
        }
        return false
    }

    private fun isValidGearPart(row: Int, column: Int) =
        row in input.indices && column in input[row].indices && input[row][column] != '.'

    fun getGearSum(): Int {
        var total = 0
        for (rowIndex in input.indices) {
            val row = input[rowIndex]
            for (gearIndex in input[rowIndex].indices) {
                if (isGear(row[gearIndex])) {
                    val sum = getAdjacentNumbersSum(rowIndex, gearIndex)
                    total += sum
                }
            }
        }
        return total
    }

    private fun isGear(c: Char) = c == '*'

    private fun getAdjacentNumbersSum(rowIndex: Int, columnIndex: Int): Int {
        val numbers = ArrayList<Int>()
        val visited = hashSetOf(rowIndex to columnIndex)
        val directions = listOf(1 to 0, -1 to 0, 1 to 1, -1 to 1, 1 to -1, -1 to -1, 0 to -1, 0 to 1)
        for (direction in directions) {
            val row = direction.first + rowIndex
            val column = direction.second + columnIndex
            if (isGearValid(row, column) && row to column !in visited) {
                val number = getNumber(row, column, visited)
                numbers.add(number)
            }
        }
        return if (numbers.size != 2) 0 else numbers[0] * numbers[1]
    }

    private fun getNumber(row: Int, column: Int, visited: HashSet<Pair<Int, Int>>): Int {
        visited.add(row to column)
        val start = getStartIndex(row, column, visited)
        val end = getEndIndex(row, column, visited)
        val line = input[row]
        val substring = line.substring(start, end + 1)
        return Integer.parseInt(substring)
    }

    private fun getEndIndex(row: Int, column: Int, visited: HashSet<Pair<Int, Int>>): Int {
        var end = column
        while (end + 1 in input.indices && input[row][end + 1].isDigit()) {
            end++
            visited.add(row to end)
        }
        return end
    }

    private fun getStartIndex(row: Int, column: Int, visited: HashSet<Pair<Int, Int>>): Int {
        var start = column
        while (start - 1 in input.indices && input[row][start - 1].isDigit()) {
            start--
            visited.add(row to start)
        }
        return start
    }

    private fun isGearValid(row: Int, column: Int) =
        row in input.indices && column in input[row].indices && input[row][column].isDigit()
}
