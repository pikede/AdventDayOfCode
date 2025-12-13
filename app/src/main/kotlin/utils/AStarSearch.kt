package utils

import java.util.*

fun <Node> aStarSearch(
    start: Node,
    isEnd: (Node) -> Boolean,
    next: (Node) -> Iterable<Pair<Node, Int>>,
    heuristicCostToEnd: (Node) -> Int
): AStarResult<Node>? {

    val costFromStart: MutableMap<Node, Int> = mutableMapOf(start to 0)
    val estimatedTotalCost: MutableMap<Node, Int> = mutableMapOf(start to heuristicCostToEnd(start))
    val cameFrom: MutableMap<Node, Node> = mutableMapOf()
    val openVertices = PriorityQueue<Node>(compareBy { estimatedTotalCost[it] ?: Int.MAX_VALUE })

    openVertices.add(start)

    while (openVertices.isNotEmpty()) {
        val current = openVertices.poll()
        if (isEnd(current)) {
            return AStarResult(
                path = generatePath(current, cameFrom),
                cost = costFromStart[current]!!
            )
        }
        next(current)
            .forEach { (neighbor, cost) ->
                val possibleStartCostToNeighbor = costFromStart[current]?.plus(cost) ?: Int.MAX_VALUE
                val currentStartCostToNeighbor = costFromStart[neighbor] ?: Int.MAX_VALUE
                if (possibleStartCostToNeighbor < currentStartCostToNeighbor) {
                    costFromStart[neighbor] = possibleStartCostToNeighbor
                    estimatedTotalCost[neighbor] =
                        possibleStartCostToNeighbor + heuristicCostToEnd(neighbor)
                    cameFrom[neighbor] = current
                    if (neighbor !in openVertices) openVertices.add(neighbor)
                }
            }
    }

    return null
}

fun <Node> generatePath(end: Node, cameFrom: Map<Node, Node>): List<Node> {
    return generateSequence(end) { cameFrom[it] }.toList().asReversed()
}

class AStarResult<Node>(
    val path: List<Node>,
    val cost: Int
)