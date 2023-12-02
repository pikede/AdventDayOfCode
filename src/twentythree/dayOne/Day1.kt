package twentythree.dayOne

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayOne/file.txt"))

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
        this.contains("one") -> 1
        this.contains("two") -> 2
        this.contains("three") -> 3
        this.contains("four") -> 4
        this.contains("five") -> 5
        this.contains("six") -> 6
        this.contains("seven") -> 7
        this.contains("eight") -> 8
        this.contains("nine") -> 9
        else -> -1
    }
}