package year2015.day11

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day11/file").toMutableList()

fun main() {
    val solution = Day11Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day11Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        var candidate = puzzleInput[0]
        while (!candidate.isIncreasingStraight() || !candidate.hasNoInvalidLetters() || !candidate.hasTwoDifferentNonOverlappingPairs()) {
            candidate = candidate.incrementPassword()
        }
        return candidate
    }

    override fun part2(): Any {
        var candidate = puzzleInput[0].incrementPassword()
        while (!candidate.isIncreasingStraight() || !candidate.hasNoInvalidLetters() || !candidate.hasTwoDifferentNonOverlappingPairs()) {
            candidate = candidate.incrementPassword()
        }
        return candidate
    }
}
fun String.incrementPassword(): String {
    val arr = this.toCharArray()
    when (arr.last()) {
        'z' -> {
            // handle overflow of adding 1 by finding index to add 1 to
            var targetIndex = arr.lastIndex
            for (index in arr.lastIndex downTo 0) {
                if (arr[index] != 'z') {
                    targetIndex = index
                    break
                }
            }
            arr[targetIndex]++

            //  As is in base 'z', set the remaining indexes to 'a'
            for (i in targetIndex + 1..arr.lastIndex) {
                arr[i] = 'a'
            }
        }
        else -> {
            arr[arr.lastIndex]++
        }
    }
    return String(arr)
}

fun String.isIncreasingStraight(): Boolean {
    if (this.length < 3) {
        return false
    }
    for (i in 2..this.lastIndex) {
        if (this[i] == this[i - 1] + 1 && this[i - 1] == this[i - 2] + 1) {
            return true
        }
    }
    return false
}

fun String.hasNoInvalidLetters(): Boolean {
    val invalidCharacter = setOf('i', 'o', 'l')
    return invalidCharacter.any { it in this }.not()
}

fun String.hasTwoDifferentNonOverlappingPairs(): Boolean {
    return this.hasPairWithoutOverlap()
}

private fun String.hasPairWithoutOverlap(): Boolean {
    if (this.length <= 2) {
        return false
    }
    val pairs = HashMap<String, ArrayList<Int>>()
    for (i in this.indices) {
        if (i + 2 > this.length) {
            break
        }
        val candidate = this.substring(i, i + 2)
        // only pairs like aa, bb, cc, ... is valid
        if (candidate[0] != candidate[1]) {
            continue
        }
        pairs[candidate] = pairs.getOrDefault(candidate, ArrayList()).also { it.add(i) }
    }
    var total = 0
    for ((_, locations) in pairs) {
        when {
            locations.size == 1 -> total++
            hasNonOverlap(locations.toHashSet()) -> total++
        }
    }
    return total >= 2
}

private fun hasNonOverlap(locations: HashSet<Int>): Boolean {
    for (i in locations) {
        locations += locations - i - (i - 1) - (i + 1)
    }
    return locations.isNotEmpty()
}
