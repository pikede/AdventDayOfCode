package twentythree.dayTwentyOne

import util.isValid
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwentyOne/file.txt"))
private fun main() {
    val pedometer = Pedometer(input)
    println(pedometer.part1(64))
}

private class Pedometer(val map: MutableList<String>) {
    val grid = map //getCharArrayGrid(map)

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
                    if (isGarden(next)) {
                        map[i]!!.add(next)
                    }
                }
            }
        }
        return map[limit]!!.size + 1 // for start
    }

    private fun isGarden(next: Pair<Int, Int>): Boolean {
        return isValid(next, grid) && grid[next.first][next.second] == '.'
    }

    fun getNextPositions(current: Pair<Int, Int>): HashSet<Pair<Int, Int>> {
        val (row, column) = current
        val up = row - 1 to column
        val down = row + 1 to column
        val left = row to column - 1
        val right = row to column + 1
        return hashSetOf(up, left, right, down)
    }

    private fun getStart(): Pair<Int, Int> {
        grid.forEachIndexed { row, chars ->
            val column = chars.indexOf('S')
            if (column != -1) {
                return row to column
            }
        }
        return -1 to -1
    }
}
