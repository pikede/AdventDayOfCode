package twentythree.dayTwentyOne

import utils.Move
import utils.isValid
import utils.move
import org.aoc.utils.readInput
import java.util.*

private val input= readInput("twentythree/dayTwentyOne/file").toMutableList()
private fun main() {
    val pedometer = Pedometer(input)
    println(pedometer.part1(64))
}

private class Pedometer(val map: MutableList<String>) {
    val grid = map

    fun part1(limit: Int): Int {
        val start = getStart()
        val q = LinkedList<Pair<Int, Int>>()
        q.offer(start)
        val map = HashMap<Int, HashSet<Pair<Int, Int>>>()
        map[0] = hashSetOf(start)
        for (i in 1..limit) {
            map[i] = hashSetOf()
            map[i - 1]!!.forEach { currentPosition ->
                for (next in getNextPositions(currentPosition)) {
                    if (isPositionAGarden(next)) {
                        map[i]!!.add(next)
                    }
                }
            }
        }
        return map[limit]!!.size + 1 // add 1 for start
    }

    private fun isPositionAGarden(next: Pair<Int, Int>): Boolean {
        return isValid(next, grid) && grid[next.first][next.second] == '.'
    }

    
    fun getNextPositions(current: Pair<Int, Int>): Set<Pair<Int, Int>> {
        return buildSet {
            add(current.move(Move.up))
            add(current.move(Move.down))
            add(current.move(Move.left))
            add(current.move(Move.right))
        }
    }

    private fun getStart(): Pair<Int, Int> {
        grid.forEachIndexed { rowIndex, chars ->
            val columnIndex = chars.indexOf('S')
            if (columnIndex != -1) {
                return rowIndex to columnIndex
            }
        }
        return -1 to -1
    }
}
