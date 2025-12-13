package yeartwentyone.twentyfive

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwentyone/twentyfive/file") as ArrayList<String>
    SeaCucumbers(input)
}

class SeaCucumbers(val input: ArrayList<String>) {

    init {
        partOne()        // 334
        partOneType2()   // 334
    }

    fun partOne() {
        val prev = LandingPlace(input)
        val current = LandingPlace(input)
        prev.step()
        for (step in 1..500) {
            val prevBoard = prev.getBoard()
            val cur = current.getBoard()

            if (prevBoard.equalMatrices(cur)) {
                println(step)
                break
            }
            prev.step()
            current.step()
        }
    }

    private fun partOneType2() {
        val setOfSteps: MutableSet<String> = mutableSetOf()
        val prev = LandingPlace(input)

        for (step in 1..500) {
            prev.step()
            val stepString = prev.getMatrixAsString()

            if (setOfSteps.contains(stepString)) {
                println(step)
                break
            } else {
                setOfSteps.add(stepString)
            }
        }
    }

    // safe place to land == same arrangement between two positions
}

class LandingPlace(val input: ArrayList<String>) {
    private val matrix = Array(input.size) { CharArray(input[0].length) { '.' } }
    private val rowLimit = input.size
    private val colLimit = input[0].length

    init {
        for (row in input.indices) {
            for (col in input[row].indices) {
                matrix[row][col] = input[row][col]
            }
        }
    }

    fun step() {
        moveEast()
        moveSouth()
    }

    private fun moveEast() {
        for (row in matrix.indices) {
            val location = ArrayList<Int>()

            for (col in matrix[row].indices) {
                val temp = matrix[row][(col + 1) % colLimit]
                if (matrix[row][col] == '>' && temp != 'v' && temp == '.') {
                    location.add(col)
                }
            }
            for (col in location) {
                val newCol = (col + 1) % (colLimit)
                matrix[row][col] = '.'
                matrix[row][newCol] = '>'
            }
        }
    }

    private fun moveSouth() {
        val location = ArrayList<Pair<Int, Int>>()
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                val temp = matrix[(row + 1) % (rowLimit)][col]
                if (matrix[row][col] == 'v' && temp != '>' && temp == '.') {
                    location.add(Pair(row, col))
                }
            }
        }

        for ((row: Int, col: Int) in location) {
            val newRow = (row + 1) % (rowLimit)
            matrix[row][col] = '.'
            matrix[newRow][col] = 'v'
        }
    }

    fun getMatrixAsString(): String {
        val landingString = StringBuilder()

        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                landingString.append(matrix[row][col])
            }
        }

        return landingString.toString()
    }

    fun printBoard() {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                print("${matrix[row][col]}, ")
            }
            println()
        }
    }

    fun getBoard(): Array<CharArray> {
        return matrix
    }

}

// do this using set and check if the matrix contains the string already
fun Array<CharArray>.equalMatrices(other: Array<CharArray>): Boolean {
    for (row in this.indices) {
        for (col in this[row].indices) {
            if (other[row][col] != this[row][col]) {
                return false
            }
        }
    }

    return true
}

// part 2 requires the rest to be completed LOL

