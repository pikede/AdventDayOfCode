package twentythree.dayNine

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayNine/file.txt"))

private fun main() {
    val sequenceCalculator = SequenceCalculator(input)
    println(sequenceCalculator.getExtrapolatedValueSum())
    println(sequenceCalculator.getExtrapolatedValueSumInReverse())
}

class SequenceCalculator(private val inputSequences: MutableList<String>) {
    private val sequences = ArrayList<Sequence>()

    init {
        parseSequences()
    }

    private fun parseSequences() {
        for (input in inputSequences) {
            val longs = parseStringToLong(input)
            sequences.add(Sequence(longs))
        }
    }

    private fun parseStringToLong(rawSequence: String): ArrayList<Long> {
        val sequence = ArrayList<Long>()
        rawSequence.split(" ").forEach {
            if (it.isNotEmpty()) {
                sequence.add(it.toLong())
            }
        }
        return sequence
    }

    fun getExtrapolatedValueSum(): Long {
        var sum = 0L
        for (sequence in sequences) {
            val lastValueInSequence = getExtrapolatedValue(sequence)
            sum += lastValueInSequence
        }
        return sum
    }

    private fun getExtrapolatedValue(sequence: Sequence): Long {
        val levelsMap = mapLevelsToSequence(sequence)
        val startingIndex = levelsMap.size - 1
        for (i in startingIndex - 1 downTo 0) {
            val left = levelsMap[i]!!.getLastValue()
            val down = levelsMap[i + 1]!!.getLastValue()
            levelsMap[i]!!.addToSequence(left + down)
        }
        return levelsMap[0]!!.getLastValue()
    }

    fun getExtrapolatedValueSumInReverse(): Long {
        var sum = 0L
        for (sequence in sequences) {
            val lastValueInSequence = getExtrapolatedValueInReverse(sequence)
            sum += lastValueInSequence
        }
        return sum
    }

    private fun getExtrapolatedValueInReverse(sequence: Sequence): Long {
        val levelsMap = mapLevelsToSequence(sequence)
        val startingIndex = levelsMap.size - 1
        for (i in startingIndex - 1 downTo 0) {
            val right = levelsMap[i]!!.getFirstValue()
            val down = levelsMap[i + 1]!!.getFirstValue()
            levelsMap[i]!!.addToSequence(0, right - down)
        }
        return levelsMap[0]!!.getFirstValue()
    }

    private fun mapLevelsToSequence(sequence: Sequence): HashMap<Int, Sequence> {
        val levelsMap = HashMap<Int, Sequence>()
        var index = 0
        levelsMap[index] = sequence
        while (levelsMap[index]!!.hasNotFinished()) {
            val currentLevel = levelsMap[index]!!
            val nextSequence = getNextSequence(currentLevel)
            index++
            levelsMap[index] = nextSequence.copy()
        }
        return levelsMap
    }

    private fun getNextSequence(sequence: Sequence): Sequence {
        val currentSequence = sequence.getValues()
        val nextSequenceValues = ArrayList<Long>()
        var i = 0
        while (i + 1 in currentSequence.indices) {
            val first = currentSequence[i]
            val second = currentSequence[i + 1]
            nextSequenceValues.add(second - first)
            i++
        }
        return Sequence(nextSequenceValues)
    }
}

private data class Sequence(private val values: ArrayList<Long>) {

    fun hasNotFinished() = values.any { it != 0L }

    fun getLastValue() = values.last()

    fun getFirstValue() = values.first()

    fun addToSequence(value: Long) {
        values += value
    }

    fun addToSequence(index: Int, value: Long) {
        values.add(index, value)
    }

    fun getValues() = values
}