package twenty5.day10

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.min

private typealias Buttons = MutableList<Int>

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day10/file.txt"))

private fun main() {
    println(Day10.part1())  //  514
    println(Day10.part2())
}

private object Day10 : AOCPuzzle {

    private val requirements = quizInput.map {
        JoltageRequirements.from(it)
    }

    override fun part1(): Any {
        val minCounts = mutableListOf<Int>()
        requirements.forEach {
            val buttonPresses = mutableListOf<Buttons>()
            val counts = mutableListOf<Int>(Int.MAX_VALUE)
            getFewestButtonPresses(
                it.availableButtons,
                it.indicators,
                buttonPresses,
                String(it.emptyIndicators),
                counts,
                0
            )
            val min = counts.min() ?: Int.MIN_VALUE
            minCounts += min
        }
        println(minCounts)
        return minCounts.sum()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getFewestButtonPresses(
        availableButtons: List<Buttons>,
        targetIndicator: String,
        pressedButtons: MutableList<Buttons>,
        currentIndicator: String,
        counts: MutableList<Int>,
        count: Int
    ) {
        if (counts[0] == 1) return
        if (counts.min()!! <= count) return
        if (currentIndicator == targetIndicator) {
            counts[0] = minOf(count, counts[0])
            return
        }
        if (pressedButtons.size > 10) return

        for (i in availableButtons) {
            val next = currentIndicator.applyButtons(i)
            pressedButtons.add(i)
            getFewestButtonPresses(
                availableButtons,
                targetIndicator,
                pressedButtons,
                next,
                counts,
                count + 1
            )
            pressedButtons.removeLast()
        }
    }

    private fun String.applyButtons(buttons: List<Int>): String {
        val arr = this.toCharArray()
        for (i in buttons) {
            arr[i] = if (this[i] == '.') '#' else '.'
        }
        return String(arr)
    }

    override fun part2(): Any {
        return 0L
    }

    private data class JoltageRequirements(
        val indicators: String,
        val availableButtons: List<Buttons>,
        val joltages: List<Int>
    ) {
        val emptyIndicators = CharArray(indicators.length) { '.' }

        companion object {
            fun from(input: String): JoltageRequirements {
                val first = input.indexOf(']')
                val last = input.indexOf("{")
                val indicator = input.substring(1, first)
                val joltage = input.substring(last + 1).replace("}", "").trim().split(",").map { it.toInt() }
                val buttons = input.substring(first + 1, last).split(" ")
                    .map {
                        it.drop(1).dropLast(1)
                            .split(",")
                            .filter { it.isNotEmpty() }
                            .map { it.toInt() } as Buttons
                    }
                    .filter { it.isNotEmpty() }
                return JoltageRequirements(indicator, buttons, joltage)
            }
        }
    }
}

