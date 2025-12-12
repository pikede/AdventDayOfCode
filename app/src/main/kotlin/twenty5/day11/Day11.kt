package twenty5.day11

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput: List<String> = readInput("twenty5/day11/file")

private fun main() {
    println(Day11.part1()) // 786
    println(Day11.part2()) // 495845045016588
}

private object Day11 : AOCPuzzle {
    private const val TARGET_REACTOR_OUT = "out"
    private val reactors = buildMap {
        quizInput.map {
            val reactors = it.replace(":", "").split(" ")
            val origin = reactors.first()
            val destinations = reactors.drop(1)
            this[origin] = destinations
        }
    }

    override fun part1(): Any {
        val q = mutableListOf("you")
        var count = 0
        while (q.isNotEmpty()) {
            val size = q.size
            for (i in 0 until size) {
                val node = q.removeFirst()
                if (node == TARGET_REACTOR_OUT) {
                    count++
                    continue
                }
                val next = reactors[node]!!
                next.onEach { q += it }
            }
        }
        return count
    }

    override fun part2(): Any {
        return buildPaths(
            current = "svr",
            required = setOf("fft", "dac"),
            reachedPath = setOf(),
            memo = mutableMapOf()
        )
    }

    private fun buildPaths(
        current: String,
        required: Set<String>,
        reachedPath: Set<String>,
        memo: MutableMap<Pair<String, Set<String>>, Long>
    ): Long {

        return memo.getOrPut(current to reachedPath) {
            if (current == TARGET_REACTOR_OUT) {
                if (required == reachedPath) 1L else 0L
            } else {
                reactors[current]!!.sumOf { next ->
                    buildPaths(next, required, if (next in required) reachedPath + next else reachedPath, memo)
                }
            }
        }
    }
}

