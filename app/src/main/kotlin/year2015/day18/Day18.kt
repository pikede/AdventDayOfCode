package year2015.day18

import AOCPuzzle
import utils.Move
import org.aoc.utils.readInput

private val quizInput= readInput("year2015/day18/file")
private const val NUMBER_OF_STEPS = 100

private fun main() {
    val solution = Day18Solution
    println(solution.part1())
    println(solution.part2())
}

private object Day18Solution : AOCPuzzle {
    private val initial = quizInput.map { it.toMutableList() }

    override fun part1(): Any {
        var copy = ArrayList(initial)
        repeat(NUMBER_OF_STEPS) {
            val result = ArrayList<MutableList<Char>>()
            for (r in copy.indices) {
                val level = ArrayList<Char>()
                for (c in copy[r].indices) {
                    level += when (copy[r][c]) {
                        '#' -> {
                            if (countOnNeighbors(copy, r, c) in 2..3) {
                                '#'
                            } else {
                                '.'
                            }
                        }
                        else -> { //'.' dot scenario
                            if (countOnNeighbors(copy, r, c) == 3) {
                                '#'
                            } else {
                                '.'
                            }
                        }
                    }
                }
                result += ArrayList(level)
            }
            copy = result
        }
        return copy.sumBy { it.count { light -> light == '#' } }
    }

    override fun part2(): Any {
        var copy = ArrayList(initial)
        val n = copy.size
        val m = copy[0].size
        val corners = hashSetOf(0 to 0, n - 1 to 0, 0 to m - 1, n - 1 to m - 1)
        for ((r, c) in corners) {
            copy[r][c] = '#'
        }
        repeat(NUMBER_OF_STEPS) {
            val result = ArrayList<MutableList<Char>>()
            for (r in copy.indices) {
                val level = ArrayList<Char>()
                for (c in copy[r].indices) {
                    level += when {
                        r to c in corners -> '#'
                        copy[r][c] == '#' -> {
                            if (countOnNeighbors(copy, r, c) in 2..3) {
                                '#'
                            } else {
                                '.'
                            }
                        }
                        copy[r][c] == '.' -> { //'.' dot scenario
                            if (countOnNeighbors(copy, r, c) == 3) {
                                '#'
                            } else {
                                '.'
                            }
                        }
                        else -> throw IllegalArgumentException("Invalid character encountered at row: $r col: $c")
                    }
                }
                result += ArrayList(level)
            }
            copy = result
        }
        return copy.sumBy { it.count { light -> light == '#' } }
    }

    private fun countOnNeighbors(grid: ArrayList<MutableList<Char>>, row: Int, col: Int): Int {
        val directions =
            listOf(Move.up, Move.left, Move.down, Move.right, Move.downLeft, Move.downRight, Move.upRight, Move.upLeft)
        var count = 0
        for ((y, x) in directions) {
            val r = y + row
            val c = x + col
            if (r in grid.indices && c in grid[r].indices && grid[r][c] == '#') {
                count++
            }
        }
        return count
    }
}
