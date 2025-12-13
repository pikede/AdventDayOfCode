package yeartwentyone.fourteen

import org.aoc.utils.readInput
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {
    val input = readInput("yeartwentyone/fourteen/file") as ArrayList
    println(Polymers(input).partOne())  // 2740
    println(Polymers(input).partTwo())  // 2959788056211
}

class Polymers(val input: ArrayList<String>) {
    private var template = ""
    private val instructions = ArrayList<Pair<String, String>>()

    init {
        loop@ for (i in input) {
            when {
                i.isEmpty() -> continue@loop
                i.contains(" -> ") -> {
                    val instruction = i.split(" -> ")
                    instructions.add(Pair(instruction[0], instruction[1]))
                }
                else -> template = i
            }
        }
    }

    fun partOne(): Int {
        for (steps in 0 until 10) {
            var index = 0
            var initial = ""
            while (index + 2 <= template.length) {
                val part = template.substring(index, index + 2)
                val map = instructionOnly()
                if (map.contains(part)) {
                    val pair = map[part] ?: ' '
                    val replaceWith = part.replace(part, "${part[0]}$pair${part[1]}")
                    if (initial.isNotEmpty() && initial.last() == replaceWith.first()) {
                        initial = initial.substring(0, initial.length - 1)
                    }
                    initial += replaceWith
                }

                index++
            }
            template = initial
        }
        return getRange(template)
    }

    // can be improved using instructions as a map of Pair <char, char> char
    @OptIn(ExperimentalUnsignedTypes::class)
    fun partTwo(): ULong {
        val polymers = template.toList()
        var pairMap: MutableMap<Pair<Char, Char>, ULong> = polymers.windowed(2)
            .map { it.first() to it.last() }
            .countBy { it }
            .mapValues { it.value.toULong() }.toMutableMap()

        for (steps in 0 until 40) {
            val rule = instructionOnly()

            var temp = HashMap<Pair<Char, Char>, ULong>().withDefault { 0UL }

            for ((i: Pair<Char, Char>, count: ULong) in pairMap.entries) {
                val target = "" + i.first + i.second
                if (!rule.contains(target)) {
                    continue
                }

                val pair = rule[target] ?: ' '
                val firstPair = Pair(i.first, pair)
                val secondPair = Pair(pair, i.second)
                temp[firstPair] = temp.getValue(firstPair) + count
                temp[secondPair] = temp.getValue(secondPair) + count

            }
            pairMap = temp.toMutableMap()
        }

        val missing = Pair(polymers.last(), polymers.first())
        pairMap[missing] = pairMap[missing]?.plus(1UL) ?: 0UL
        return getPairMapRange(pairMap)
    }

    private fun <T, K> Collection<T>.countBy(keySelector: (T) -> K): Map<K, Int> {
        return this.groupingBy(keySelector).eachCount()
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    private fun getPairMapRange(pairMap: MutableMap<Pair<Char, Char>, ULong>): ULong {
        var characterMap: MutableMap<Char, ULong> = mutableMapOf<Char, ULong>().withDefault { 0UL }

        for ((i: Pair<Char, Char>, cnt: ULong) in pairMap.entries) {
            val (first: Char, second: Char) = i

            characterMap[i.first] = characterMap.getValue(first) + cnt
            characterMap[i.second] = characterMap.getValue(second) + cnt
        }

        characterMap = characterMap.mapValues { it.value / 2UL }.toMutableMap()

        val max: Map.Entry<Char, ULong> = characterMap.maxBy { it.value }!!
        val min: Map.Entry<Char, ULong> = characterMap.minBy { it.value }!!

        return max.value - min.value
    }

    private fun instructionOnly(): HashMap<String, Char> {
        val map = HashMap<String, Char>()

        for (i in instructions) {
            map[i.first] = i.second[0]
        }

        return map
    }

    private fun getRange(template: String): Int {
        val map = mutableMapOf<Char, Int>().withDefault { 0 }

        for (element in template) {
            map[element] = map.getOrDefault(element, 0) + 1
        }

        val max = map.maxBy { it.value / 2 }!!.value
        val min = map.minBy { it.value }!!.value

        // using priority queue
        val queue = PriorityQueue<Int> { a, b -> b- a }
//        queue.addAll(map.values)
//        return  queue.first() - queue.last()

//        OR

        return max - min
    }
}