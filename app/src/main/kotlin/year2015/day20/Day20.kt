package year2015.day20

import AOCPuzzle

private const val TARGET_PRESENTS = 33100000

private fun main() {
    val day20 = Day20Solution
    println(day20.part1())
    println(day20.part2())
}

private object Day20Solution : AOCPuzzle {

    override fun part1(): Any {
        return getLowestHouseNumber( 10, Int.MAX_VALUE)
    }

    override fun part2(): Any {
        return getLowestHouseNumber(11, 50)
    }

    private fun getLowestHouseNumber(increments: Int, houseLimit: Int): Int {
        val d = TARGET_PRESENTS / increments
        val counts = MutableList(d) { 0 }
        for (i in 1..d) {
            for (j in 1..minOf(d / i, houseLimit)) {
                counts[j * i - 1] += i * increments
            }
            if (counts[i - 1] >= TARGET_PRESENTS) {
                return i
            }
        }
        return -1
    }
}
