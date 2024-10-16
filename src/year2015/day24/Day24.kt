package year2015.day24

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day24/file.txt"))

private fun main() {
    val day24 = Day24Solution
    println(day24.part1())
    println(day24.part2())
}

private object Day24Solution : AOCPuzzle {
    val allPresents = quizInput.map { it.toInt() } as ArrayList
    val allPresentsSum: Int = allPresents.sum()
    val groups = HashSet<List<Int>>()

    override fun part1(): Any {
        buildGroupEntanglements(0, allPresents, arrayListOf(), allPresentsSum / 3)
        return getMinimumEntanglement()
    }

    override fun part2(): Any {
        buildGroupEntanglements(0, allPresents, arrayListOf(), allPresentsSum / 4)
        return getMinimumEntanglement()
    }

    fun getMinimumEntanglement(): Long {
        val sortedGroups = groups.toList().sortedBy { it.size }
        val smallestSize = sortedGroups.first().size
        val matchingEntanglements = sortedGroups.filter { it.size == smallestSize }
        var targetEntanglement = Long.MAX_VALUE
        for (i in matchingEntanglements) {
            var currentEntanglement = 1L
            i.forEach {
                currentEntanglement *= it
            }
            targetEntanglement = minOf(currentEntanglement, targetEntanglement)
        }
        return targetEntanglement
    }

    private fun buildGroupEntanglements(
        index: Int,
        presents: ArrayList<Int>,
        group1: java.util.ArrayList<Int>,
        target: Int
    ) {
        val isGroupSumValid = group1.sum() == target
        if (isGroupSumValid) {
            groups += arrayListOf(group1.sortedDescending())
            return
        }
        for (start in index..presents.lastIndex) {
            val i = presents[start]
            if (i in group1) {
                continue
            }
            if (i !in group1 && group1.sum() + i <= target) {
                group1 += i
                buildGroupEntanglements(start + 1, presents, group1, target)
                group1.removeAt(group1.lastIndex)
            }
        }
    }
}
