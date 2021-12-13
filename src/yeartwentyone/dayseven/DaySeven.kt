package yeartwentyone.dayseven

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/dayseven/file.txt")) as ArrayList<String>
    println(Crabs(input).partOne())   //  344297
    println(Crabs(input).partTwo())   //  97164301
}

class Crabs(val input: ArrayList<String>) {
    private val positions = ArrayList<Int>()
    private val fuels = ArrayList<Int>()

    init {
        parseInput()
    }

    private fun parseInput() {
        val splitInput = input[0].split(",")
        for (i in splitInput) {
            positions.add(Integer.parseInt(i))
        }
    }

    fun partOne(): Int {
        val max = positions.max() ?: 0
        for (i in 0..max) {
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
        val max = positions.max() ?: 0

        for (i in 0..max) {
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

