package twentytwo.eight

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/eight/file.txt"))

fun main() {
    println(partA())  //1688
    println(partB())  //410400
}

fun partA(): Int {
    var matrix = Array(input.size) { IntArray(input[0].length) { 0 } }
    var total = 0

    for (r in input.indices) {
        for (c in input[r].indices) {
            matrix[r][c] = input[r][c] - '0'
            if (r == 0 || c == 0 || r == matrix.lastIndex || c == matrix[r].lastIndex) {
                total++
            }
        }
    }

    fun checkLeft(r: Int, c: Int): Boolean {
        for (i in 0 until c) {
            if (matrix[r][c] <= matrix[r][i]) {
                return false
            }
        }
        return true
    }

    fun checkRight(r: Int, c: Int): Boolean {
        for (i in c + 1..matrix[0].lastIndex) {
            if (matrix[r][c] <= matrix[r][i]) {
                return false
            }
        }
        return true
    }

    fun checkUp(r: Int, c: Int): Boolean {
        for (i in r - 1 downTo 0) {
            if (matrix[r][c] <= matrix[i][c]) {
                return false
            }
        }
        return true
    }

    fun checkDown(r: Int, c: Int): Boolean {
        for (i in r + 1 until matrix.size) {
            if (matrix[r][c] <= matrix[i][c]) {
                return false
            }
        }
        return true
    }

    fun isTreeHouse(r: Int, c: Int): Boolean {
        return checkUp(r, c) || checkDown(r, c) || checkLeft(r, c) || checkRight(r, c)
    }

    for (r in matrix.indices) {
        for (c in matrix[r].indices) {
            if (r == 0 || c == 0 || r == matrix.lastIndex || c == matrix[r].lastIndex) {
                continue
            }
            if (isTreeHouse(r, c)) total++
        }
    }

    return total
}

fun partB(): Long {
    val matrix = Array(input.size) { IntArray(input[0].length) { 0 } }
    var total = 0

    for (r in input.indices) {
        for (c in input[r].indices) {
            matrix[r][c] = input[r][c] - '0'
            if (r == 0 || c == 0 || r == matrix.lastIndex || c == matrix[r].lastIndex) {
                total++
            }
        }
    }

    fun checkLeft(r: Int, c: Int): Long {
        var cnt = 0L
        for (i in c-1 downTo 0) {
            if (matrix[r][c] >= matrix[r][i]) {
                cnt++
                if (matrix[r][c] == matrix[r][i]) {
                    return cnt
                }
            } else {
                cnt++
                return cnt
            }
        }
        return cnt
    }

    fun checkRight(r: Int, c: Int): Long {
        var cnt = 0L
        for (i in c + 1..matrix[0].lastIndex) {
            if (matrix[r][c] >= matrix[r][i]) {
                cnt++
                if (matrix[r][c] == matrix[r][i]) {
                    return cnt
                }
            } else {
                cnt++
                return cnt
            }
        }
        return cnt
    }

    fun checkUp(r: Int, c: Int): Long {
        var cnt = 0L
        for (i in r - 1 downTo 0) {
            if (matrix[r][c] >= matrix[i][c]) {
                cnt++
                if (matrix[r][c] == matrix[i][c]) {
                    return cnt
                }
            } else {
                cnt++
                break
            }
        }
        return cnt
    }

    fun checkDown(r: Int, c: Int): Long {
        var cnt = 0L
        for (i in r + 1 until matrix.size) {
            if (matrix[r][c] >= matrix[i][c]) {
                cnt++
                if (matrix[r][c] == matrix[i][c]) {
                    return cnt
                }
            } else {
                cnt++
                break
            }
        }
        return cnt
    }

    fun getScenicScore(r: Int, c: Int): Long {
        if (r == 3 && c == 2) {
            println()
            println(checkUp(r, c))
            println(checkLeft(r, c))
            println(checkDown(r, c))
            println(checkRight(r, c))
            println()
        }
        return checkUp(r, c) * checkDown(r, c) * checkLeft(r, c) * checkRight(r, c)
    }

    var max = 0L
    for (r in matrix.indices) {
        for (c in matrix[r].indices) {
            max = maxOf(max, getScenicScore(r, c))
        }
    }

    return max
}
// 5752800













