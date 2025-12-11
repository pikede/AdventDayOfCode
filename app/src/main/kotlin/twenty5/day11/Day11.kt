package twenty5.day11

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput: List<String> = readInput("twenty5/day11/file")

private fun main() {
    println(Day11.part1())
    println(Day11.part2())
}

private object Day11 : AOCPuzzle {
    val reactors = buildMap {
        quizInput.map {
            val reactors = it.replace(":", "").split(" ")
            val origin = reactors.first()
            val destinations = reactors.drop(1)
            this[origin] = destinations
        }
    }

    override fun part1(): Any {
        val q = mutableListOf("you")
        val visited = mutableSetOf<String>()
        var count = 0
        while (q.isNotEmpty()) {
            val size = q.size
            for (i in 0 until size) {
                val node = q.removeFirst()
                if (node == "out") {
                    count++
                    continue
                }
//                if (node in visited) continue
//                visited += node
                val next = reactors[node]!!
                next.onEach {
                     q += it
                }
            }
            println(q)
        }
        return count
    }

    override fun part2(): Any {
        return 0
    }
}

