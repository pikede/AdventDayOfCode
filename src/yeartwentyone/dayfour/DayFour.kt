package yeartwentyone.dayfour

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/dayfour/file.txt")) as ArrayList<String>
    val two = ArrayList(input)

    println(Bingo(input).partOne())   // 34506
    println(Bingo(two).partTwo())    //  7686
}

class Bingo(val input: ArrayList<String>) {

    fun partOne(): Int {
        // parse drawn numbers into a list of string
        val drawnNumbers = input[0].split(",")
        var drawn = 0

        input.removeAt(0)
        input.removeAt(0)

        val numbersDrawn = ArrayList<Int>()

        while (drawn < drawnNumbers.size) {
            val board = ArrayList<String>()
            var index = 0
            while (index < input.size) {
                when {
                    input[index] == input.last() || input[index].isEmpty() -> {
                        if (input[index] == input.last()) {
                            board.add(input[index])
                        }
                            numbersDrawn.add(Integer.parseInt(drawnNumbers[drawn]))

                        val playersBoard = BingoBoard(board, numbersDrawn)
                        playersBoard.createBingoBoard()
                        if (playersBoard.isWinner()) {
                            return playersBoard.getFinalScore()
                        } else {
                            board.clear()
                        }
                    }
                    else -> {
                        board.add(input[index])
                    }
                }
                index++
            }
            drawn++
        }
        return -1
    }

    fun partTwo(): Int {
        // parse drawn numbers into a list of string
        val drawnNumbers = input[0].split(",")
        var drawn = 0

        input.removeAt(0) // removes drawn numbers
        input.removeAt(0) // removes empty space

        val allBoards = ArrayList<ArrayList<String>>()

        val board = ArrayList<String>()
        for (i in input) {
            if (i.isNotEmpty()) {
                board.add(i)
            } else {
                allBoards.add(ArrayList(board))
                board.clear()
            }
        }
        allBoards.add(ArrayList(board))

        val numbersDrawn = ArrayList<Int>()
        while (drawn < drawnNumbers.size) {
            numbersDrawn.add(Integer.parseInt(drawnNumbers[drawn]))

            var start = 0

            //stops when only one board is left -> last winner
            while (start < allBoards.size && allBoards.size > 1) {
                val playersBoard = BingoBoard(allBoards[start], numbersDrawn)
                playersBoard.createBingoBoard()
                if (playersBoard.isWinner()) {
                    allBoards.removeAt(start)
                }

                start++
            }

            // returns the last bingo winner
            if (allBoards.size == 1) {
                val lastWinnerBoard = BingoBoard(allBoards[0], numbersDrawn)
                lastWinnerBoard.createBingoBoard()
                if (lastWinnerBoard.isWinner()) {
                    return lastWinnerBoard.getFinalScore()
                }
            }

            drawn++
        }

        return -1
    }
}

class BingoBoard(private var input: ArrayList<String>, private var drawnNumbers: ArrayList<Int>) {
    private val matrix = Array(input.size) { Array(input.size) { 0 } }

    fun createBingoBoard() {
        var row = 0
        while (row <= matrix.size - 1) {
            var col = 0
            val line = StringBuilder()
            val trim = input[row].trim().replace("  ", " ")
            while (col < trim.length) {
                if (trim[col] == ' ') {
                    line.append(",")
                } else {
                    line.append(trim[col])
                }
                col++
            }
            for ((index, i) in line.split(",").withIndex()) {
                matrix[row][index] = Integer.parseInt(i)
            }
            row++
        }
    }

    fun isWinner(): Boolean {
        for (row in matrix.indices) {
            if (checkRow(row)) {
                return true
            }
            if (checkColumn(row)) {
                return true
            }
        }
        return false
    }

    private fun checkRow(row: Int): Boolean {
        var cnt = 0
        for (col in matrix[row]) {
            if (drawnNumbers.contains(col)) {
                cnt++
            }
        }

        return cnt >= 5
    }

    private fun checkColumn(row: Int): Boolean {
        var cnt = 0
        var col = 0
        while (col < matrix[row].size) {
            if (drawnNumbers.contains(matrix[col][row])) {
                cnt++
            }
            col++
        }

        return cnt >= 5
    }

    fun getFinalScore(): Int {
        var finalScore = 0

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (!drawnNumbers.contains(matrix[row][col])) {
                    finalScore += matrix[row][col]
                }
            }
        }

        return finalScore * drawnNumbers.last()
    }

    // To print a players Bingo board
    private fun printBoard() {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                print("${matrix[row][col]}, ")
            }
            println()
        }
    }
}
