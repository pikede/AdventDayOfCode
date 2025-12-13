package twentythree.dayThirteen

import org.aoc.utils.readInput
import kotlin.text.StringBuilder

private val input= readInput("twentythree/dayThirteen/file").toMutableList()

private fun main() {
    val patternNotes = PatternNotes(input)
    println(patternNotes.getInflectionPointSum())
    println(patternNotes.getSmudgedInflectionPointSum())
}

class PatternNotes(val input: MutableList<String>) {
    private val notes = ArrayList<Notes>()

    init {
        parseNotes()
    }

    private fun parseNotes() {
        var note = mutableListOf<String>()
        for (i in input) {
            if (i.isEmpty()) {
                notes.add(Notes(note))
                note = mutableListOf()
            } else {
                note.add(i)
            }
        }
        notes.add(Notes(note))
    }

    fun getInflectionPointSum(): Int {
        var total = 0
        for (i in notes) {
            total += i.getInflectionPoint()
        }
        return total
    }

    fun getSmudgedInflectionPointSum(): Int {
        var total = 0
        for (i in notes) {
            total += i.getSmudgedInflectionPoint()
        }
        return total
    }
}

data class Notes(val note: MutableList<String>) {

    fun getInflectionPoint(): Int {
        val columns = getColumnStrings().values.toList()
        val rows = note
        val column = getMirrorIndex(columns) ?: 0
        val row = getMirrorIndex(rows) ?: 0
        return row * 100 + column
    }

    fun getSmudgedInflectionPoint(): Int {
        val columns = getColumnStrings().values.toMutableList()
        val rows = note
        val column = fixSmudge(columns) ?: 0
        val row = fixSmudge(rows) ?: 0
        return row * 100 + column
    }

    private fun fixSmudge(pattern: MutableList<String>): Int? {
        for (index in 1..pattern.lastIndex) {
            val before = pattern.drop(index)
            val after = pattern.reversed().drop(pattern.size - index)
            var count = 0
            for (rowIndex in 0..minOf(before.lastIndex, after.lastIndex)) {
                count += before[rowIndex].indices.count { before[rowIndex][it] != after[rowIndex][it] }
            }
            if (count == 1) {
                return index
            }
        }
        return null
    }

    private fun fixSmudgeWithZip(pattern: MutableList<String>): Int? {
        for (index in 1..pattern.lastIndex) {
            val before = pattern.drop(index)
            val after = pattern.reversed().drop(pattern.size - index)
            val mirrored = before.zip(after)
            val diff = mirrored.sumBy { (first, second) ->
                first.zip(second).count { pair -> pair.first != pair.second }
            }
            if (diff == 1) {
                return index
            }
        }
        return null
    }

    private fun getColumnStrings(): HashMap<Int, String> {
        val map = HashMap<Int, String>()
        for (c in note[0].indices) {
            val sb = StringBuilder()
            for (i in note) {
                sb.append(i[c])
            }
            map[c] = sb.toString()
        }
        return map
    }

    private fun getMirrorIndexWithDrop(pattern: List<String>): Int? {
        val hashes = pattern.map(String::hashCode)
        for (index in 1..hashes.lastIndex) {
            val before = hashes.drop(index).asSequence()
            val after = hashes.asReversed().drop(hashes.size - index).asSequence()
            val mirroredPairs = before.zip(after)
            if (mirroredPairs.all { (first, second) -> first == second }) {
                return index
            }
        }
        return null
    }

    private fun getMirrorIndex(pattern: List<String>): Int? {
        for (index in 1 until pattern.size) {
            val before = getAllAfterIndex(pattern, index)
            val after = getAllAfterIndex(pattern.reversed(), pattern.size - index)
            if (areEqual(before, after)) {
                return index
            }
        }
        return null
    }

    private fun getAllAfterIndex(list: List<String>, index: Int): List<String> {
        val sb = ArrayList<String>()
        for (i in index until list.size) {
            sb.add(list[i])
        }
        return sb
    }

    private fun areEqual(list1: List<String>, list2: List<String>): Boolean {
        val min = minOf(list1.lastIndex, list2.lastIndex)
        for (index in 0..min) {
            if (list1[index] != list2[index]) {
                return false
            }
        }
        return true
    }
}