package year2015.day12

import AOCPuzzle
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day12/file.txt"))

fun main() {
    val solution = Day12Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day12Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        return puzzleInput.fold(0) { totalSum, integerCandidate ->
            val candidates = integerCandidate.split("{", "[", ":", "\"", "}", "]", ",")
            var sum = 0
            candidates.forEach {
                sum += it.getIntValue()
            }
            totalSum + sum
        }
    }

    override fun part2(): Any {
        val array = JSONArray(puzzleInput[0])
        return try {
            getValue(array)
        } catch (e: Exception) {
            0
        }
    }

    fun getValue(puzzleObject: Any?): Int {
        if (puzzleObject is Int) return puzzleObject
        if (puzzleObject is String) return 0
        var total = 0
        if (puzzleObject is JSONArray) {
            for (i in 0 until puzzleObject.length()) {
                try {
                    val `val` = getValue(puzzleObject[i])
                    total += `val`
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return total
        }
        if (puzzleObject is JSONObject) {
            val names = puzzleObject.names()
            for (i in 0 until names.length()) {
                val name = names?.get(i) as String
                total += if (puzzleObject[name] == "red") {
                    return 0
                } else {
                    getValue(puzzleObject[name])
                }
            }
            return total
        }
        return 0
    }

    private fun String.getIntValue(): Int {
        return try {
            Integer.parseInt(this)
        } catch (e: Exception) {
            0
        }
    }
}

