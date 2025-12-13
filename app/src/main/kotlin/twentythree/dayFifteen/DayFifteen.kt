package twentythree.dayFifteen

import utils.zipWithIndex
import org.aoc.utils.readInput

private val input= readInput("twentythree/dayFifteen/file").toMutableList()

private fun main() {
    val asciiCalculator = AsciiCalculator(input)
    println(asciiCalculator.calculateHashSum())   // 516070
    println(asciiCalculator.calculateFocusSum())  // 244981
}

class AsciiCalculator(lensesInput: MutableList<String>) {
    private val sequence = ArrayList<String>()

    init {
        sequence.addAll(lensesInput[0].split(","))
    }

    fun calculateHashSum(): Int {
        return sequence.fold(0) {acc, current -> acc + getHash(current) }
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

private fun getHash(currentString: String): Int {
    return currentString.fold(0) { acc, current -> ((acc + current.code) * 17) % 256 }
}