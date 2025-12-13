package yeartwentyone.daytwo

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwentyone/daytwo/file")
    val p1 = Position(input)
    println(p1.partOne())    // 1924923
    println(p1.partTwo())    // 1982495697
}

class Position(private val input: List<String>) {

    fun partOne(): Int {
        var horizontal = 0
        var depth = 0

        for (i in input) {
//            val moveBy = i[i.length - 1].toString().toInt() OR
//            val moveBy = Integer.parseInt(i[i.length - 1].toString()) OR
            val moveBy = i[i.length - 1] - '0'
            when {
                i.contains("forward") -> {
                    horizontal += moveBy
                }
                i.contains("up") -> {
                    depth -= moveBy
                }
                else -> {
                    // down
                    depth += moveBy
                }
            }
        }

        return horizontal * depth
    }

    fun partTwo(): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        for (i in input) {
            val moveBy = i[i.length - 1] - '0'
            when {
                i.contains("forward") -> {
                    horizontal += moveBy
                    depth += (aim * moveBy)
                }
                i.contains("up") -> {
                    aim -= moveBy
                }
                else -> {
                    // down
                    aim += moveBy
                }
            }
        }

        return horizontal * depth
    }
}
