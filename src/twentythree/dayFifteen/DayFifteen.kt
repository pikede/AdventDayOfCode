package twentythree.dayFifteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFifteen/file.txt"))

private fun main() {
    val asciiCalculator = AsciiCalculator(input)
    println(asciiCalculator.calculateHashSum())
}

class AsciiCalculator(private val input: MutableList<String>) {
    private val sequence = ArrayList<String>()

    init {
        sequence.addAll(input[0].split(","))
    }

    fun calculateHashSum(): Int {
        var sum = 0
        for (i in sequence) {
            sum += getAsciiValue(i)
        }
        return sum
    }

    private fun getAsciiValue(currentString: String): Int {
        var sum = 0
        currentString.forEach {
            sum += it.toInt()
            sum *= 17
            sum %= 256
        }
        return sum
    }
}