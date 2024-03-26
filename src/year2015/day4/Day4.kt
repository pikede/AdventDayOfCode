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
        return day4("yzbqklnj", "00000")
    }

    override fun part2(): Any {
        return day4("yzbqklnj", "000000")
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun day4(input: String, match: String): Int {
        fun md5It(n: Int, input: String): String {
            return md5("$input$n")
        }

        tailrec fun loop(i: Int, hash: String): Int {
            return if (hash.startsWith(match)) {
                i
            } else {
                loop(i + 1, md5It(i + 1, input))
            }
        }

        return loop(0, md5It(0, input))
    }
}
// 61 62 63 64 65 66 -> 