package twentythree.dayTwelve

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwelve/file.txt"))

private fun main() {
    val calculator = PermutationCalculator(input)
    println(calculator.calculateArrangements())

}

class PermutationCalculator(private val inputCombinations: MutableList<String>) {
    private val springArrangements = ArrayList<Arrangements>()

    init {
        parseInput()
    }

    private fun parseInput() {
        for (i in inputCombinations) {
            val arrangementAndCount = i.split(" ")
            val springs = arrangementAndCount[0]
            val counts = getCounts(arrangementAndCount[1])
            val temp = Arrangements(springs, counts)
            springArrangements.add(temp)
        }
    }

    private fun getCounts(counts: String): java.util.ArrayList<Int> {
        val order = ArrayList<Int>()
        for (s in counts.split(",")) {
            order.add(s.toInt())
        }
        return order
    }

    fun calculateArrangements(): Int {
        var totalArrangements = 0
        for (i in springArrangements.indices) {
            val arrangement = getArrangement(springArrangements[i])
            totalArrangements += arrangement
        }
        return totalArrangements
    }

    private fun getArrangement(arrangement: Arrangements): Int {
        val ans = HashSet<String>()
        val original = arrangement.springs.toCharArray()
        val copy = arrangement.springs.toCharArray().copyOf()
        buildUsingBackTracking(0, original, ans, copy, arrangement.orders)
        return ans.size
    }

    private fun buildUsingBackTracking(
        index: Int,
        original: CharArray,
        ans: HashSet<String>,
        copy: CharArray,
        orders: ArrayList<Int>
    ) {
        if (index >= original.size) {
            if (getOrder(copy) == orders) {
                ans.add(String(copy))
            }
            return
        }
        if (copy[index] == '?') {
            val hashCopy = copy.copyOf()
            hashCopy[index] = '#'
            val dotCopy = copy.copyOf()
            dotCopy[index] = '.'
            buildUsingBackTracking(index + 1, original, ans, hashCopy, orders)
            buildUsingBackTracking(index + 1, original, ans, dotCopy, orders)
        } else {
            buildUsingBackTracking(index + 1, original, ans, copy, orders)
        }
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

data class Arrangements(val springs: String, var orders: ArrayList<Int>)