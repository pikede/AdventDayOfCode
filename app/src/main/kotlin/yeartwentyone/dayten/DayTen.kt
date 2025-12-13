package yeartwentyone.dayten

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwentyone/dayten/file")
    val solution = SyntaxScoring(input as ArrayList)
    println(solution.partOne())   //  318099
    println(solution.partTwo())   //  2389738699
}

class SyntaxScoring(var input: ArrayList<String>) {
    fun partOne(): Int {
        val mapPartOne = HashMap<Char, Int>()
        mapPartOne[')'] = 3
        mapPartOne[']'] = 57
        mapPartOne['}'] = 1197
        mapPartOne['>'] = 25137

        var sumIllegal = 0

        for (i in input) {
            val prev = i.getIllegalPart()

            for (k in prev) {
                if (mapPartOne.containsKey(k)) {
                    sumIllegal += mapPartOne[k] ?: 0
                    break
                }
            }

        }

        return sumIllegal
    }

    fun partTwo(): Long {
        val map = HashMap<Char, Int>()
        map['('] = 1
        map['['] = 2
        map['{'] = 3
        map['<'] = 4

        val incomplete = ArrayList<Long>()

        for (i in input) {
            val prev = i.getIllegalPart()

            if (!prev.contains('}') && !prev.contains(')') && !prev.contains('>') && !prev.contains(']')) {
                var score = 0L
                for (k in prev.reversed()) {
                    score *= 5
                    score += map[k] ?: 0
                }
                incomplete.add(score)
            }
        }
        incomplete.sort()

        return incomplete[(incomplete.size / 2)]
    }

    private fun String.getIllegalPart(): String {
        var temp = this
        var prev = ""

        while (prev != temp) {
            prev = temp
            temp = temp.replace("[]", "").replace("{}", "").replace("<>", "").replace("()", "")
        }

        return prev
    }
}
