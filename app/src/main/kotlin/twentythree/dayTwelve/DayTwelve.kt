package twentythree.dayTwelve

import org.aoc.utils.readInput

private val input= readInput("twentythree/dayTwelve/file").toMutableList()

private fun main() {
    val calculator = PermutationCalculator(input)
    println(calculator.calculateArrangements())
    println(calculator.calculateAppendedArrangements())
}

class PermutationCalculator(private val inputCombinations: MutableList<String>) {
    private val regularSpringArrangements = ArrayList<Arrangements>()
    private val appendedArrangements = ArrayList<Arrangements>()
    private val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

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

    private fun getArrangement(arrangement: Arrangements): Int {
        val ans = HashSet<String>()
        val copy = arrangement.springs.toCharArray().copyOf()
        buildUsingBackTracking(0, ans, copy, arrangement.orders)
        return ans.size
    }

    fun calculateAppendedArrangements(): Long { // Uses DP
        var totalArrangements = 0L
        for (i in appendedArrangements.indices) {
            val arrangement = testSprings(appendedArrangements[i].springs, appendedArrangements[i].orders)
            totalArrangements += arrangement
        }
        return totalArrangements
    }

    private fun testSprings(map: String, damageList: List<Int>): Long {
        if (map.isEmpty()) {
            return if (damageList.isNotEmpty()) {
                0L
            } else {
                1L
            }
        }

        if (map.startsWith(".")) return testSprings(map.substring(1), damageList)
        else if (map.startsWith("?")) {
            return (testSprings(map.substring(1), damageList)
                    + testSprings("#${map.substring(1)}", damageList).also { cache[map to damageList] = it })
        } else {
            // Check cache and shortcut
            cache[map to damageList]?.let { return it }
            val sum = when {
                damageList.isEmpty() -> 0L
                map.length < damageList.first() -> 0L
                map.substring(0, damageList.first()).contains('.') -> 0L
                map.length == damageList.first() -> {
                    if (damageList.size == 1) {
                        1L
                    } else {
                        0L
                    }
                }
                map[damageList.first()] == '#' -> 0L
                else -> testSprings(map.substring(damageList.first() + 1), damageList.drop(1))
            }

            cache[map to damageList] = sum
            return sum
        }
    }

    private fun buildUsingBackTracking(
        index: Int,
        ans: HashSet<String>,
        copy: CharArray,
        orders: ArrayList<Int>
    ) {
        if (index >= copy.size) {
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
            buildUsingBackTracking(index + 1, ans, hashCopy, orders)
            buildUsingBackTracking(index + 1, ans, dotCopy, orders)
        } else {
            buildUsingBackTracking(index + 1, ans, copy, orders)
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

data class Arrangements(var springs: String, var orders: ArrayList<Int>)