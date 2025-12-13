package twentythree.daySeventeen

import org.aoc.utils.readInput
import utils.Move
import utils.Point2D
import utils.aStarSearch
import org.aoc.utils.readInput
import java.util.*

private val input = readInput("twentythree/daySeventeen/file").toMutableList()

private fun main() {
    val crucibleHeatCalculator = CrucibleHeatCalculator(input)
    println(crucibleHeatCalculator.getLeastHeatCost(movesPerStraightLine = 1..3))
    println(crucibleHeatCalculator.getLeastHeatCost(movesPerStraightLine = 4..10))
}

class CrucibleHeatCalculator(private val inputCrucibles: MutableList<String>) {
    fun getLeastHeatCost(movesPerStraightLine: IntRange): Int {
        val start = Point2D(x = 0, y = 0)
        val end = Point2D(x = inputCrucibles[0].lastIndex, y = inputCrucibles.lastIndex)

        val path = aStarSearch(
            start = Node(start, null),
            next = { it.next(movesPerStraightLine) },
            isEnd = { it.position == end },
            heuristicCostToEnd = { it.position.distanceTo(end) }
        ) ?: error("no path found")
        return path.cost
    }

    
    private fun Node.next(movesPerStraightLine: IntRange): List<Pair<Node, Int>> {
        val moves = when (prevMove) {
            Move.left, Move.right -> listOf(Move.up, Move.down)
            Move.up, Move.down -> listOf(Move.left, Move.right)
            else -> listOf(Move.up, Move.down, Move.left, Move.right)
        }

        return buildList {
            for (move in moves) {
                var cost = 0
                var nextPosition = position
                for (i in 1..movesPerStraightLine.last) {
                    nextPosition = nextPosition.applyMove(move)
                    val (col, row) = nextPosition
                    if (row !in inputCrucibles.indices || col !in inputCrucibles[row].indices) {
                        continue
                    }
                    cost += (inputCrucibles[row][col] - '0')
                    if (i >= movesPerStraightLine.first) {
                        add(Node(nextPosition, move) to cost)
                    }
                }
            }
        }
    }
}

private data class Node(
    val position: Point2D,
    val prevMove: Move?
)