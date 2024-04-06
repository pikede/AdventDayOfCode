package year2015.day5

import AOCPuzzle
import twentythree.dayTwentyFive.locations
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day5/file.txt"))

fun main() {
    val solution = Day5Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day5Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        return puzzleInput.fold(0) { niceStrings, currentString ->
            niceStrings + if (currentString.hasMinVowels() && currentString.hasConsecutive() && currentString.containsInvalid()) 1 else 0
        }
    }

    override fun part2(): Any {
        return puzzleInput.fold(0) { niceStrings, currentString ->
            niceStrings + if (currentString.hasPairWithoutOverlap() && currentString.repeatsWithLetterInBetween()) 1 else 0
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
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
        pairs[candidate] = pairs.getOrDefault(candidate, ArrayList()).also { it.add(i) }
    }
    var total = 0
    for ((_, candidate) in pairs) {
        if (locations.size >= 2) {
            total += countNonOverlaps(candidate.toHashSet())
        }
    }
    return total >= 2
}

private fun countNonOverlaps(locations: HashSet<Int>): Int {
    for (i in locations) {
        locations += locations - i - (i - 1) - (i + 1)
    }
    return locations.size
}

private fun String.repeatsWithLetterInBetween(): Boolean {
    if (this.length <= 2) {
        return false
    }
    for (i in 2..this.lastIndex) {
        if (this[i] == this[i - 2]) {
            return true
        }
    }
    return false
}

private fun String.hasMinVowels(min: Int = 3): Boolean {
    val vowels = setOf('a', 'e', 'i', 'o', 'u')
    return this.count { it in vowels } >= min
}

private fun String.hasConsecutive(): Boolean {
    if (this.length <= 1) {
        return false
    }
    for (i in 1..this.lastIndex) {
        if (this[i] == this[i - 1]) {
            return true
        }
    }
    return false
}

private fun String.containsInvalid(): Boolean {
    val invalid = setOf("ab", "cd", "pq", "xy")
    for (i in invalid) {
        if (i in this) {
            return false
        }
    }
    return true
}
