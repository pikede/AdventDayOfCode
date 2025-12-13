package year2015.day12

import AOCPuzzle
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day12/file").toMutableList()

fun main() {
    val solution = Day12Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day12Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        val candidates = puzzleInput[0].split("{", "[", ":", "\"", "}", "]", ",")
        return candidates.fold(0) { acc, it ->
            acc + it.getIntegerValue()
        }
    }

    private fun String.getIntegerValue(): Int {
        return try {
            Integer.parseInt(this)
        } catch (e: Exception) {
            0
        }
    }

    override fun part2(): Any {
        val array = JSONArray(puzzleInput[0])
        return try {
            getSumOfIntegers(array)
        } catch (e: Exception) {
            0
        }
    }

    private fun getSumOfIntegers(puzzleObject: Any?): Int {
        var total = 0
        return when (puzzleObject) {
            is Int -> puzzleObject
            is String -> 0
            is JSONArray -> {
                for (i in 0 until puzzleObject.length()) {
                    try {
                        val `val` = getSumOfIntegers(puzzleObject[i])
                        total += `val`
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                return total
            }
            is JSONObject -> {
                val names = puzzleObject.names()
                for (i in 0 until names.length()) {
                    val name = names?.get(i) as String
                    total += if (puzzleObject[name] == "red") {
                        return 0
                    } else {
                        getSumOfIntegers(puzzleObject[name])
                    }
                }
                return total
            }
            else -> 0
        }
    }
}
