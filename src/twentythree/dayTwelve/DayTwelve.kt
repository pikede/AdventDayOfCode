package twentythree.dayTwelve

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwelve/file.txt"))

private fun main() {
    val calculator = PermutationCalculator(input)
//    println(calculator.calculateArrangements())
    println(calculator.calculateAppendedArrangements())
}

class PermutationCalculator(private val inputCombinations: MutableList<String>) {
    private val regularSpringArrangements = ArrayList<Arrangements>()
    private val appendedArrangements = ArrayList<Arrangements>()

    init {
        parseInput()
    }

    private fun parseInput() {
        for (i in inputCombinations) {
            val arrangementAndCount = i.split(" ")
            val springs = arrangementAndCount[0]
            val counts = getCounts(arrangementAndCount[1])
            val temp = Arrangements(springs, counts)
            regularSpringArrangements.add(temp)
            val appendedSprings = getAppendedSprings(springs)
            val appendedCounts = getAppendedCounts(arrangementAndCount[1])
            val tempAppended = Arrangements(appendedSprings, appendedCounts)
            appendedArrangements.add(tempAppended)
        }
    }

    private fun getAppendedCounts(counts: String): ArrayList<Int> {
        val sb = StringBuilder(counts)
        repeat(4) {
            sb.append(",$counts")
        }
        return getCounts(sb.toString())
    }

    private fun getAppendedSprings(springs: String): String {
        val sb = StringBuilder(springs)
        repeat(4) {
            sb.append("?$springs")
        }
        return sb.toString()
    }

    private fun getCounts(counts: String): ArrayList<Int> {
        val order = ArrayList<Int>()
        for (s in counts.split(",")) {
            order.add(s.toInt())
        }
        return order
    }

    fun calculateArrangements(): Int {
        var totalArrangements = 0
        for (i in regularSpringArrangements.indices) {
            val arrangement = getArrangement(regularSpringArrangements[i])
            totalArrangements += arrangement
        }
        return totalArrangements
    }

    // USE DP
    fun calculateAppendedArrangements(): Int {
        var totalArrangements = 0
        for (i in appendedArrangements.indices) {
            val arrangement = getArrangement(appendedArrangements[i])
            println("${i + 1}  is $arrangement")
            totalArrangements += arrangement
        }
        return totalArrangements
    }

    private fun getArrangement(arrangement: Arrangements): Int {
        val ans = HashSet<String>()
        val copy = arrangement.springs.toCharArray().copyOf()
        val map = HashMap<Int, Int>()
        val count = arrangement.springs.count { it == '?' }
        map[count] = 0
        buildUsingBackTracking(0, ans, copy, arrangement.orders, map, count)
        println(map)
        return map[0]!! // ans.size
    }

    private fun buildUsingBackTracking(
        index: Int,
        ans: HashSet<String>,
        copy: CharArray,
        orders: ArrayList<Int>,
        map: HashMap<Int, Int>,
        count: Int
    ) : Int {
        if (map[count] != null) {
            return map[count]!!
        }
        if (index >= copy.size) {
            if (getOrder(copy) == orders) {
                ans.add(String(copy))
                map[count] = (map[count] ?: 0 ) + 1
            }
            return map[count]!!
        }

        if (copy[index] == '?') {
            val hashCopy = copy.copyOf()
            hashCopy[index] = '#'
            val dotCopy = copy.copyOf()
            dotCopy[index] = '.'
            buildUsingBackTracking(index + 1, ans, hashCopy, orders, map, count - 1)
            buildUsingBackTracking(index + 1, ans, dotCopy, orders, map, count - 1)
        } else {
            buildUsingBackTracking(index + 1, ans, copy, orders, map, count)
        }
        map[count] = (map[count-1] ?:0) + 1
        return count
    }

    private fun getOrder(copy: CharArray): List<Int> {
        val order = ArrayList<Int>()
        var count = 0
        for (i in copy) {
            if (i == '#') {
                count++
            } else if (count > 0) {
                order.add(count)
                count = 0
            }
        }
        if (count > 0) {
            order.add(count)
        }
        return order
    }
}

data class Arrangements(var springs: String, var orders: ArrayList<Int>)
data class ArrangementInput(var Index: Int, var srings: CharArray)