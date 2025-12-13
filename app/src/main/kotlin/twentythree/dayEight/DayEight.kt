package twentythree.dayEight

import utils.greatestCommonDivisor
import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEight/file.txt"))

private fun main() {
    val map = Map(input)
    println(map.countStepsFromStartToEnd())
    println(map.countStepsFromEveryNodeAToNodeZ())
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
        val startingNode = "AAA"
        val endNode = "ZZZ"
        var numberOfSteps = 0
        var currentNode = startingNode
        while (true) {
            for (i in instructions) {
                if (currentNode == endNode) {
                    return numberOfSteps
                }
                currentNode = when (i) {
                    'R' -> directions[currentNode]!!.right
                    else -> directions[currentNode]!!.left
                }
                numberOfSteps++
            }
        }
        return numberOfSteps
    }

    fun countStepsFromEveryNodeAToNodeZ(): Long {
        val startingNodes = getStartingNodes()
        val steps = ArrayList<Long>()
        startingNodes.forEach {
            steps += countStepsFromStartToEnd(it).toLong()
        }
        var lowestCommonMultiple = steps[0]
        for (i in 1 until steps.size) {
            lowestCommonMultiple = getLCM(lowestCommonMultiple, steps[i])
        }
        return lowestCommonMultiple
    }

    private fun getLCM(first: Long, second: Long): Long {
        return (first * second) / greatestCommonDivisor(first.toInt(), second.toInt())
    }

    private fun countStepsFromStartToEnd(startingNode: String): Int {
        var numberOfSteps = 0
        var currentNode = startingNode
        while (true) {
            for (i in instructions) {
                if (currentNode.last() == 'Z') {
                    return numberOfSteps
                }
                currentNode = when (i) {
                    'R' -> directions[currentNode]!!.right
                    else -> directions[currentNode]!!.left
                }
                numberOfSteps++
            }
        }
        return numberOfSteps
    }

    private fun getStartingNodes(): MutableList<String> {
        return directions.filter { it.key.last() == 'A' }.keys.toMutableList()
    }
}

data class Direction(val left: String, val right: String)
