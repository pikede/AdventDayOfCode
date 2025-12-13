package year2015.day6

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("src/year2015/day6/file").toMutableList()

fun main() {
    val solution = Day6Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day6Solution(puzzleInput: MutableList<String>) : AOCPuzzle {
    private var instructions: List<Instruction> = puzzleInput.map { instruction ->
        parseInstruction(
            instruction, when {
                instruction.contains("turn off") -> LightAction.TurnOff
                instruction.contains("turn on") -> LightAction.TurnOn
                instruction.contains("toggle") -> LightAction.Toggle
                else -> {
                    throw Exception("Invalid type")
                }
            }
        )
    }

    override fun part1(): Any {
        val grid = Array(1000) { IntArray(1000) { 0 } }
        instructions.forEach { coordinates ->
            val (startRow, startColumn, endRow, endColumn, action) = coordinates
            for (r in startRow..endRow) {
                for (c in startColumn..endColumn) {
                    grid[r][c] = when (action) {
                        LightAction.TurnOn -> 1
                        LightAction.TurnOff -> 0
                        LightAction.Toggle -> {
                            if (grid[r][c] == 1) 0 else 1
                        }
                    }
                }
            }
        }
        return grid.fold(0) { acc, currentLights ->
            acc + currentLights.sum()
        }
    }

    override fun part2(): Any {
        val grid = Array(1000) { IntArray(1000) { 0 } }
        instructions.forEach { coordinates ->
            val (startRow, startColumn, endRow, endColumn, action) = coordinates
            for (r in startRow..endRow) {
                for (c in startColumn..endColumn) {
                    grid[r][c] += when (action) {
                        LightAction.TurnOn -> 1
                        LightAction.TurnOff -> if (grid[r][c] - 1 >= 0) -1 else 0
                        LightAction.Toggle -> 2
                    }
                }
            }
        }
        return grid.fold(0) { acc, currentLights ->
            acc + currentLights.sum()
        }
    }

    private fun parseInstruction(instruction: String, action: LightAction): Instruction {
        val coordinates = when (action) {
            LightAction.TurnOn -> instruction.replace("turn on ", "")
            LightAction.TurnOff -> instruction.replace("turn off ", "")
            LightAction.Toggle -> instruction.replace("toggle ", "")
        }.replace(" through ", " ")
        val (start, end) = coordinates.split(" ")
        val (startY, startX) = start.split(",").map { it.toInt() }
        val (endY, endX) = end.split(",").map { it.toInt() }
        return Instruction(
            startY,
            startX,
            endY,
            endX,
            action
        )
    }
}

private data class Instruction(
    val startRow: Int,
    val startCol: Int,
    val endRow: Int,
    val endColumn: Int,
    val action: LightAction
)

private enum class LightAction {
    TurnOn,
    TurnOff,
    Toggle
}