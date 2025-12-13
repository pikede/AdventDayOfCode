package yeartwentyone.dayeight

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwentyone/dayeight/file") as ArrayList<String>
    println(SegmentSearch(input).partOne())   // 387
    println(SegmentSearch(input).partTwo())   // 986034
}

class SegmentSearch(val input: ArrayList<String>) {
    private val segments = ArrayList<String>()

    init {
        parseInput()
    }

    private fun parseInput() {
        for (i in input) {
            val temp = i.split(" | ")
            segments.add(temp[1])
        }
    }

    fun partOne(): Int {
        var cnt = 0

        segments.forEach {
            for (i in it.split(" ")) {
                when (i.length) {
                    2, 3, 4, 7 -> {
                        cnt++
                    }
                }
            }
        }

        return cnt
    }

    fun partTwo(): Int {
        var sum = 0

        for (i in input.indices) {
            val split = input[i].split(" | ")
            val pattern = split[0]
            val map = getMap(pattern)

            val decode = segments[i].split(" ")
            val temp = StringBuilder()

            for (k in decode) {
                temp.append(getMatch(k, map))
            }

            sum += Integer.parseInt(temp.toString())
        }

        return sum
    }

    private fun getMatch(find: String, map: HashMap<Int, String>): Int {
        val set = map.values.toList() as ArrayList<String>
        for (i in set) {
            if (i.length == find.length && !i.isMissing(find)) {
                for (m in map.keys) {
                    if (map[m] == i) return m
                }
            }
        }
        return 0
    }

    private fun getMap(key: String): HashMap<Int, String> {
        val map = HashMap<Int, String>()
        val sorted = key.split(" ").sortedBy { it.length }
        val original = sorted.reversed() as ArrayList<String>
        for (i in sorted) {
            when (
                i.length) {
                2 -> {
                    map[1] = i
                    original.remove(i)
                }
                4 -> {
                    map[4] = i
                    original.remove(i)
                }
                3 -> {
                    map[7] = i
                    original.remove(i)
                }
                7 -> {
                    map[8] = i
                    original.remove(i)
                }
            }
        }

        for (i in original) {
            when {
                i.length == 6 && !i.isMissing(map[4] ?: "") -> map[9] = i
                i.length == 6 && i.isMissing(map[1] ?: "") -> map[6] = i
                i.length == 5 && !i.isMissing(map[7] ?: "") -> map[3] = i
                i.length == 5 && ((map[9] ?: "").toSet() - i.toSet()).size == 2 -> map[2] = i
                i.length == 5 && i.isMissing(map[9] ?: "") -> map[5] = i
                else -> map[0] = i
            }
        }

        return map
    }

    private fun String.isMissing(compareWith: String): Boolean {
        return !this.toSet().containsAll(compareWith.toSet())
    }
}