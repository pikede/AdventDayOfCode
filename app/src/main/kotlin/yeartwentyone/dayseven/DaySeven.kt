package yeartwentyone.dayseven

import org.aoc.utils.readInput
import kotlin.math.abs

fun main() {
    val input = readInput("yeartwentyone/dayseven/file") as ArrayList<String>
    println(Crabs(input).partOne())   //  344297
    println(Crabs(input).partTwo())   //  97164301
}

class Crabs(val input: ArrayList<String>) {
    private val positions = ArrayList<Int>()
    private val fuels = ArrayList<Int>()
    var max : Int? = 0

    init {
        parseInput()
    }

    private fun parseInput() {
        val splitInput = input[0].split(",")
        for (i in splitInput) {
            positions.add(Integer.parseInt(i))
        }
        max = positions.max() ?: 0
    }

    fun partOne(): Int {
        for (i in 0..max!!) {
            var sum = 0
            for (j in positions) {
                val temp = abs(i - j)
                sum += temp
            }
            fuels.add(sum)
        }

        return fuels.min() ?: -1
    }

    fun partTwo(): Int {
        for (i in 0..max!!) {
        var min = 0
            for (j in positions) {
                val temp = abs(i - j)
                for (k in 0..temp) {
                    min += k
                }
            }
            fuels.add(min)
        }

        return fuels.min() ?: -1
    }
}

