package yeartwentyone.thirteen

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/thirteen/file.txt")) as ArrayList<String>
    println(Fold(input).partOne())  //  810
    Fold(input).partTwo()  //  HLBUBGFR
}

class Fold(val input: ArrayList<String>) {
    private var maxRow = Int.MIN_VALUE
    private var maxCol = Int.MIN_VALUE
    private var dotCoordinates = ArrayList<Pair<Int, Int>>()
    private var instructions = ArrayList<Pair<String, Int>>()

    init {
        loop@ for (i in input) {
            when {
                i.isEmpty() -> continue@loop
                i.contains(",") -> {
                    val pair = i.split(",")
                    val row = Integer.parseInt(pair[0])
                    val col = Integer.parseInt(pair[1])
                    maxRow = maxOf(col, maxRow)
                    maxCol = maxOf(row, maxCol)
                    dotCoordinates.add(Pair(col, row))
                }
                i.contains("=") -> {
                    val split = i.replace("fold along ", "").split("=")
                    instructions.add(Pair(split[0], Integer.parseInt(split[1])))
                }
            }
        }
    }

    fun partOne(): Int {
        val paper = Paper(dotCoordinates, maxRow + 1, maxCol + 1)

        val firstInstruction = instructions[0]
        when (firstInstruction.first) {
            "x" -> paper.foldCOL(firstInstruction.second)
            "y" -> paper.foldRow(firstInstruction.second)
        }

        return paper.cntDots()
    }

    // Prints out (#) and (.) letters
    fun partTwo() {
        val paper = Paper(dotCoordinates, maxRow + 1, maxCol + 1)

        for (pair in instructions) {
            when (pair.first) {
                "x" -> paper.foldCOL(pair.second)
                "y" -> paper.foldRow(pair.second)
            }
        }

        paper.printFoldedMatrix()
    }

}

class Paper(coordinates: ArrayList<Pair<Int, Int>>, var maxROW: Int, var maxCOL: Int) {
    private val matrix = Array(maxROW) { Array(maxCOL) { '.' } }

    init {
        for (i in coordinates) {
            val x = i.first
            val y = i.second
            matrix[x][y] = '#'
        }
    }

    fun foldCOL(newCOL: Int) {
        maxCOL = newCOL
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (col == maxCOL) {
                    matrix[row][col] = '|'
                    continue
                }
                if (col > maxCOL && matrix[row][col] == '#') {
                    matrix[row][getFoldIndex(col, maxCOL)] = matrix[row][col]
                    matrix[row][col] = '.'
                }
            }
        }
    }

    fun foldRow(newMax: Int) {
        maxROW = newMax
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (row == maxROW) {
                    matrix[row][col] = '-'
                    continue
                }
                if (row > maxROW && matrix[row][col] == '#') {
                    matrix[getFoldIndex(row, maxROW)][col] = matrix[row][col]
                    matrix[row][col] = '.'
                }
            }
        }
    }

    private fun getFoldIndex(n: Int, max: Int): Int {
        return abs(n - max - max)
    }

    fun cntDots(): Int {
        var dotsCnt = 0
        for (row in 0 until maxROW) {
            for (col in 0 until maxCOL) {
                if (matrix[row][col] == '#') {
                    dotsCnt++
                }
            }
        }
        return dotsCnt
    }

    fun printFoldedMatrix() {
        for (row in 0 until maxROW) {
            for (col in 0 until maxCOL) {
                print("${matrix[row][col]} ")
            }
            println()
        }
    }

    fun printEntireMatrix() {
        var cnt = 0
        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] == '#') {
                    cnt++
                }
                print("${matrix[row][col]} ")
            }
            println()
        }
        println(cnt)
    }
}