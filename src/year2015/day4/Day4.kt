package year2015.day4

import AOCPuzzle
import java.security.MessageDigest

fun main() {
    val solution = Day4Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day4Solution : AOCPuzzle {

    override fun part1(): Any {
        val secretKeyInput = "yzbqklnj"
        val startingZeroes = "00000"
        return loop(0, md5It(0, secretKeyInput), secretKeyInput, startingZeroes)
    }

    override fun part2(): Any {
        val secretKeyInput = "yzbqklnj"
        val startingZeroes = "000000"
        return day4(secretKeyInput, startingZeroes)
    }

    fun day4(secretKeyInput: String, match: String): Int {
        return loop(0, md5It(0, secretKeyInput), secretKeyInput, match)
    }

    fun md5It(n: Int, secretKeyInput: String): String {
        return md5("$secretKeyInput$n")
    }

    private fun md5(secretKeyInput: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(secretKeyInput.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    private tailrec fun loop(i: Int, hash: String, secretKeyInput: String, match: String): Int {
        return if (hash.startsWith(match)) {
            i
        } else {
            loop(i + 1, md5It(i + 1, secretKeyInput), secretKeyInput, match)
        }
    }
}