package twentythree.dayEight

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEight/file.txt"))

private fun main() {
    val map = Map(input)
    println(map.countStepsFromStartToEnd())
}

class Map(private val inputDirections: MutableList<String>) {
    private val instructions = input[0]
    private val directions = HashMap<String, Direction>()

    init {
        parseInputDirections()
    }

    private fun parseInputDirections() {
        inputDirections.forEach { direction ->
            if (direction.contains("=")) {
                val startToDestinations = direction.split("=")
                val starting = startToDestinations[0].trim()
                val cleanDestinations = startToDestinations[1].replace("(", "").replace(")", "")
                val destinations = cleanDestinations.split(",")
                val left = destinations[0].trim()
                val right = destinations[1].trim()
                directions[starting] = Direction(left, right)
            }
        }
    }

    fun countStepsFromStartToEnd(): Int {
        val start = "AAA"
        val end = "ZZZ"
        var numberOfSteps = 0
        var currentPosition = start
        while (true) {
            for (i in instructions) {
                if (currentPosition == end) {
                    return numberOfSteps
                }
                currentPosition = when (i) {
                    'R' -> directions[currentPosition]!!.right
                    else -> directions[currentPosition]!!.left
                }
                numberOfSteps++
            }
        }
        return numberOfSteps
    }
}

data class Direction(val left: String, val right: String)

// 9:45