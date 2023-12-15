package twentythree.dayFifteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFifteen/file.txt"))

private fun main() {
    val asciiCalculator = AsciiCalculator(input)
    println(asciiCalculator.calculateHashSum())
    println(asciiCalculator.calculateFocusSum())
}

class AsciiCalculator(private val input: MutableList<String>) {
    private val sequence = ArrayList<String>()

    init {
        sequence.addAll(input[0].split(","))
    }

    fun calculateHashSum(): Int {
        var sum = 0
        sequence.forEach {
            sum += getAsciiValue(it)
        }
        return sum
    }

    fun calculateFocusSum(): Int {
        val boxes = (0..255).associateWith { mutableMapOf<String, Int>() }.toMutableMap()
        sequence.forEach {
            when {
                it.contains('=') -> {
                    val (label, focalLength) = it.split('=')
                    val hash = getAsciiValue(label)
                    boxes[hash]!![label] = focalLength.toInt()
                }
                it.contains('-') -> {
                    val (label, _) = it.split('-')
                    val hash = getAsciiValue(label)
                    boxes[hash]!! -= label
                }
            }
        }
        var sum = 0
        boxes.entries.forEachIndexed { index, mutableEntry ->
            mutableEntry.value.entries.forEachIndexed { labelIndex, (label, focalLength) ->
                sum += (index+1) * (labelIndex + 1) * focalLength
            }
        }
        return sum
    }

    private fun getAsciiValue(currentString: String): Int {
        return currentString.fold(0) { acc, current -> ((acc + current.toInt()) * 17) % 256 }
    }
}