package year2015.day24

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day23/file.txt"))

private fun main() {
    val day24 = Day24Solution
    day24.subLists()
    println(Day24Solution.part1())
    println(Day24Solution.part2())
}

private object Day24Solution : AOCPuzzle {
//    val allPresents = arrayListOf(1, 2, 3, 4, 5, 7, 8, 9, 10, 11)
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

    //    val groups = HashSet<List<List<Int>>>()
    val groups = HashSet<List<Int>>()
    val target = allPresents.sum() / 3

/*    fun subLists() {
        val group1 = ArrayList<Int>()
        val group2 = ArrayList<Int>()
        val group3 = ArrayList<Int>()
        buildGroups(0, allPresents, group1, group2, group3)
        val smallestFirst = groups.sortedBy { it.first().size }  // todo use groupedBy
        val smallestSize = smallestFirst[0][0].size
        val matchingEntanglements = smallestFirst.filter { it[0].size == smallestSize }
        var targetEntanglement = Int.MAX_VALUE
        for (i in matchingEntanglements) {
            var currentEntanglement = 1
            i[0].forEach {
                currentEntanglement *= it
            }
            targetEntanglement = minOf(currentEntanglement, targetEntanglement)
        }
        println(targetEntanglement)
    }

    val seen = HashMap<String, ArrayList<Int>>()

    private fun buildGroups(
        index: Int,
        presents: ArrayList<Int>,
        group1: java.util.ArrayList<Int>,
        group2: java.util.ArrayList<Int>,
        group3: java.util.ArrayList<Int>
    ) {

        val seenAny = seen[group1.sortedDescending().toString() + group2.sortedDescending().toString() + group3.sortedDescending().toString()] != null
        if (seenAny) {
            return
        }

        val areSumsEqual = group1.sum() == target && group2.sum() == target && group3.sum() == target
        val areSizesEqual = (group1.size + group2.size + group3.size) == allPresents.size
        if (areSumsEqual && areSizesEqual) {
            groups += arrayListOf(group1.sortedDescending(), group2.sortedDescending(), group3.sortedDescending())
            return
        }
        for (start in index..presents.lastIndex) {
            val i = presents[start]
            if (i in group1 || i in group2 || i in group3) {
                continue
            }
            if (i !in group1 && group1.sum() + i <= target) {
                group1 += i
                buildGroups(start + 1, presents, group1, group2, group3)
                group1.removeAt(group1.lastIndex)
            }
            if (i !in group2 && group2.sum() + i <= target) {
                group2 += i
                buildGroups(start + 1, presents, group1, group2, group3)
                group2.removeAt(group2.lastIndex)
            }
            if (i !in group3 && group3.sum() + i <= target) {
                group3 += i
                buildGroups(start + 1, presents, group1, group2, group3)
                group3.removeAt(group3.lastIndex)
            }
        }
        seen[group2.sortedDescending().toString() + group3.sortedDescending().toString() + group1.sortedDescending().toString()] = (group1 + group2 + group3) as ArrayList<Int>
        seen[group3.sortedDescending().toString() + group2.sortedDescending().toString() + group1.sortedDescending().toString()] = (group1 + group2 + group3) as ArrayList<Int>
        seen[group2.sortedDescending().toString() + group1.sortedDescending().toString() + group3.sortedDescending().toString()] = (group1 + group2 + group3) as ArrayList<Int>
        seen[group1.sortedDescending().toString() + group2.sortedDescending().toString() + group3.sortedDescending().toString()] = (group1 + group2 + group3) as ArrayList<Int>
    }
    */

    fun subLists() {
        val groupings = getGroupings()
    }

    fun getGroupings(): HashSet<List<List<Int>>> {
        buildGroups(0, allPresents, arrayListOf())
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
        return hashSetOf()
    }

    private fun buildGroups(
        index: Int,
        presents: ArrayList<Int>,
        group1: java.util.ArrayList<Int>,
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
                buildGroups(start + 1, presents, group1)
                group1.removeAt(group1.lastIndex)
            }
        }
    }

    override fun part1(): Any {
        buildGroups(0, allPresents, arrayListOf())
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

    override fun part2(): Any {
        return ""
    }
}
