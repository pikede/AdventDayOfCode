package twentythree.dayTwentyFive

import org.aoc.utils.readInput
import java.util.LinkedList

private val input= readInput("twentythree/dayTwentyFive/file")
val destinations = input.map { it.split(": ") }.associate { (origin, destinations) ->
    origin to destinations.split(" ").map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet()
}.toMutableMap()

val locations = destinations.entries.flatMap { entry ->
    entry.value + entry.key
}.toMutableSet()

fun main() {
    for ((k, v) in HashMap(destinations)) {
        for (i in v) {
            destinations[i] = destinations.getOrDefault(i, hashSetOf()).also { it.add(k) }
        }
    }
}

