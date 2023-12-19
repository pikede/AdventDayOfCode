package twentythree.daySeventeen

import util.Move
import util.Point2D
import util.aStarSearch
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashSet

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/daySeventeen/file.txt"))

private fun main() {
    val crucible = Crucible(input)
    println(crucible.getLeastHeatCost(str8bound = 1..3))
    println(crucible.getLeastHeatCost(str8bound = 4..10))
}

class Crucible(val map: MutableList<String>) {
    val paths = HashSet<Pair<Int, Int>>()

    fun getLeastHeatCost(str8bound: IntRange): Int {
        val start = Point2D(x = 0, y = 0)
        val end = Point2D(x = map[0].lastIndex, y = map.lastIndex)

        val path = aStarSearch(
            start = Node(start, null),
            next = { it.next(str8bound) },
            isEnd = { it.position == end },
            heuristicCostToEnd = { it.position.distanceTo(end) }
        ) ?: error("no path found")

        /*    val q = PriorityQueue<Node>() {a, b ->
                  a.position.distanceTo(end) - b.position.distanceTo(end)
            }
            q.offer(Node(start, null))
            val visited = HashSet<Node>()
            while (q.isNotEmpty()) {
                val node = q.poll()
                if (node.position == end) {
                    break
                }
                for (next in node.next(1..3)) {
                    if (next.first !in visited) {
                        q.offer(next.first)
                        visited.add(next.first)
                    }
                }
            }
            return visited.fold(0) { acc, (point, _) -> acc + (map[point.y][point.x] - '0') }
            return visited.size*/
        return path.cost
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun Node.next(str8bound: IntRange): List<Pair<Node, Int>> {
        val moves = when (prevMove) {
            Move.left, Move.right -> listOf(Move.up, Move.down)
            Move.up, Move.down -> listOf(Move.left, Move.right)
            else -> listOf(Move.up, Move.down, Move.left, Move.right)
        }

        return buildList {
            for (move in moves) {
                var cost = 0
                var pos = position
                for (i in 1..str8bound.last) {
                    pos = pos.applyMove(move)
                    val (col, row) = pos
                    if (row !in map.indices || col !in map[row].indices) {
                        continue
                    }
                    cost += (map[row][col] - '0')
                    if (i >= str8bound.first) {
                        add(Node(pos, move) to cost)
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