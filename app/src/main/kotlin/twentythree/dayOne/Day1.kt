package twentythree.dayOne

import org.aoc.utils.readInput

private val input= readInput("twentythree/dayOne/file").toMutableList()

fun main() {
    println(calibrationOne(input))
    println(calibrationTwo(input))
}

fun calibrationOne(input: List<String>): Int {
    var total = 0
    for (currentLine in input) {
        var start = 0
        var end = currentLine.lastIndex
        while (start <= end) {
            if (currentLine[start].isDigit() && currentLine[end].isDigit()) {
                break
            }
            if (!currentLine[start].isDigit()) {
                start++
            }
            if (!currentLine[end].isDigit()) {
                end--
            }
        }
        val firstInt = (currentLine[start] - '0') * 10
        val lastInt = currentLine[end] - '0'
        total += firstInt + lastInt
    }
    return total
}

fun calibrationTwo(input: MutableList<String>): Int {
    var total = 0
    for (currentLine in input) {
        val tenthsValue = getFirstValue(currentLine) * 10
        val unitValue = getLastValue(currentLine)
        total += tenthsValue + unitValue
    }
    return total
}

fun getFirstValue(line: String): Int {
    for (i in line.indices) {
        if (line[i].isDigit()) {
            return line[i] - '0'
        } else {
            val intValue = line.substring(0, i + 1).getIntValue()
            if (intValue != -1) {
                return intValue
            }
        }
    }
    return -1
}

fun getLastValue(line: String): Int {
    for (i in line.lastIndex downTo 0) {
        if (line[i].isDigit()) {
            return line[i] - '0'
        } else {
            val intValue = line.substring(i).getIntValue()
            if (intValue != -1) {
                return intValue
            }
        }
    }
    return -1
}

fun String.getIntValue(): Int {
    return when {
        this.containsNumber(NumberAsString.One) -> NumberAsString.One.intValue
        this.containsNumber(NumberAsString.Two) -> NumberAsString.Two.intValue
        this.containsNumber(NumberAsString.Three) -> NumberAsString.Three.intValue
        this.containsNumber(NumberAsString.Four) -> NumberAsString.Four.intValue
        this.containsNumber(NumberAsString.Five) -> NumberAsString.Five.intValue
        this.containsNumber(NumberAsString.Six) -> NumberAsString.Six.intValue
        this.containsNumber(NumberAsString.Seven) -> NumberAsString.Seven.intValue
        this.containsNumber(NumberAsString.Eight) -> NumberAsString.Eight.intValue
        this.containsNumber(NumberAsString.Nine) -> NumberAsString.Nine.intValue
        else -> -1
    }
}

private fun String.containsNumber(number: NumberAsString): Boolean {
    return this.contains(number.name, true)
}

enum class NumberAsString(val intValue: Int) {
    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9);
}