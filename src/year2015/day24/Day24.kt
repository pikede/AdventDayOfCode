package year2015.day24

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day23/file.txt"))

private fun main() {
    val day24 = Day24Solution
    println(day24.part1())
    println(day24.part2())
}

private object Day24Solution : AOCPuzzle {
    //    val allPresents = arrayListOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11) Example
    val allPresents =
        arrayListOf(
            1,
            3,
            5,
            11,
            13,
            17,
            19,
            23,
            29,
            31,
            37,
            41,
            43,
            47,
            53,
            59,
            67,
            71,
            73,
            79,
            83,
            89,
            97,
            101,
            103,
            107,
            109,
            113
        )
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
        val smallestSize = sortedGroups.first().size //smallestFirst[0][0].size
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
        val areSumsEqual = group1.sum() == target
        if (areSumsEqual) {
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
