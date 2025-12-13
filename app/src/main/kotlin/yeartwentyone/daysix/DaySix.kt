package yeartwentyone.daysix

import org.aoc.utils.readInput

fun main() {

    val input = readInput("yeartwentyone/daysix/file") as ArrayList<String>
    println(LanternFish(input).partOne())  //  345387
    println(LanternFish(input).partTwo())  //  1574445493136
}

class LanternFish(private val input: ArrayList<String>) {
    private var fishes = ArrayList<Int>()

    init {
        getFishes()
    }

    private fun getFishes() {
        val temp = input[0].split(",")
        // parse string list into int list
        for (i in temp) {
            fishes.add(i[0] - '0')
        }
    }

    fun partOne(): Int {
        for (i in 1..80) {
            var start = 0
            var end = fishes.size - 1
            while (start <= end) {
                if (fishes[start] == 0) {
                    fishes[start] = 6
                    fishes.add(8)
                } else {
                    fishes[start]--
                }
                start++
            }
        }

        return fishes.size
    }

    fun partTwo(): Long {
        val days = ArrayList<Long>(9)

        // populating with zero because initial capcity didn't populate with zero
        for (i in 0..8) {
            days.add(0)
        }

        for (i in fishes) {
            days[i] += 1L
        }

        for (i in 1..256) {
            val temp = ArrayList(days)
            days[0] = temp[1]
            days[1] = temp[2]
            days[2] = temp[3]
            days[3] = temp[4]
            days[4] = temp[5]
            days[5] = temp[6]
            days[6] = temp[7] + temp[0]
            days[7] = temp[8]
            days[8] = temp[0]
        }

        return days.reduce { acc, l -> acc + l }
    }
}