package twenty5.day7

import AOCPuzzle
import utils.Direction
import utils.Point
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day7/file.txt"))

private fun main() {
    println(Day7.part1())   // 1678
    println(Day7.part2())   // 357525737893560
}

@OptIn(ExperimentalStdlibApi::class)
private object Day7 : AOCPuzzle {
    override fun part1(): Int {
        val grid = quizInput.map { it.toCharArray() }

        val col = grid.first { it.contains('S') }.indexOf('S')
        val active = mutableListOf<Point>()
        val starting = Point(y = 0, x = col)
        active.add(starting)
        val MAX_Y = grid.size
        val MAX_X = grid[0].size
        var count = 0
        while (active.isNotEmpty()) {
            val size = active.size
            for (i in 0 until size) {
                val current = active.removeFirst()
                val next = current.plus(Point(x = 0, y = 1))
                val isInBounds = next.x in 0 until MAX_X && next.y in 0 until MAX_Y
                if (isInBounds) {
                    if (next in active) continue
                    val isSplitter = grid[next.y][next.x] == '^'
                    if (isSplitter) {
                        count++
                        active += next.split()
                    } else {
                        active += next
                    }
                }
            }
        }
        return count
    }

    private fun Point.split() = listOf(this.plus(Direction.LEFT), this.plus(Direction.RIGHT))


    override fun part2(): Long {
        val col = quizInput.first { it.contains('S') }.indexOf('S')
        val grid2 = LongArray(quizInput.size).also { it[col] = 1 }

        quizInput.filterIndexed { index, _ -> index % 2 == 0 }.forEach { line ->
            (1..line.lastIndex).forEach { index ->
                if (line[index] == '^') {
                    grid2[index - 1] += grid2[index]
                    grid2[index + 1] += grid2[index]
                    grid2[index] = 0
                }
            }
        }

        return grid2.sum()
    }
}