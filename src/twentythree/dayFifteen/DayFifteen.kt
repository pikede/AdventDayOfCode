package twentythree.dayFifteen

import twentythree.util.zipWithIndex
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
            sum += getHash(it)
        }
        return sum
    }

    fun calculateFocusSum(): Int {
        val box = Box()
        return box.getFocusPower(sequence)
    }
}

class Box {
    private lateinit var boxOfLenses: MutableMap<Int, MutableMap<String, Int>>

    init {
        addEmptyBoxes()
    }

    private fun addEmptyBoxes() {
        boxOfLenses = (0..255).associateWith { mutableMapOf<String, Int>() }.toMutableMap()
    }

    fun getFocusPower(sequences: MutableList<String>): Int {
        addLensesToBoxes(sequences)
        return getTotalFocusPower()
    }

    private fun addLensesToBoxes(sequences: MutableList<String>) {
        sequences.forEach {
            when {
                it.contains("=") -> {
                    val (lense, focalLength) = it.split("=")
                    val hash = getHash(lense)
                    boxOfLenses[hash]!![lense] = focalLength.toInt()
                }
                it.contains("-") -> {
                    val (lense, _) = it.split("-")
                    val hash = getHash(lense)
                    boxOfLenses[hash]!! -= lense
                }
            }
        }
    }

    private fun getTotalFocusPower(): Int {
        return boxOfLenses.entries.fold(0) { acc, (index, lenses) ->
            acc + lenses.values.zipWithIndex(index + 1) { slotIndex, (focalLength, box) ->
                box * (slotIndex + 1) * focalLength
            }.sum()
        }
    }
}

fun getHash(currentString: String): Int {
    return currentString.fold(0) { acc, current -> ((acc + current.toInt()) * 17) % 256 }
}