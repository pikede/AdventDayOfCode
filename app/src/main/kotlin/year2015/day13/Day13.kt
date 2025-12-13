package year2015.day13

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("year2015/day13/file").toMutableList()

fun main() {
    val solution = Day13Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day13Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    private val scores = HashMap<String, HashMap<String, Int>>()

    
    private val names = buildSet {
        puzzleInput.forEach {
            val candidates = it.replace(".", "").split(" ")
            val origin = candidates.first()
            val destination = candidates.last()
            var score = Integer.parseInt(candidates[3])
            score *= if (candidates[2] == "gain") 1 else -1
            add(origin)
            add(destination)
            scores[origin] = scores.getOrDefault(origin, hashMapOf()).also { map -> map[destination] = score }
        }
    }.toHashSet()

    override fun part1(): Any {
        return getMaxHappinessFromSeatingArrangement()
    }

    override fun part2(): Any {
        addYourName()
        return getMaxHappinessFromSeatingArrangement()
    }

    private fun getMaxHappinessFromSeatingArrangement(): Int {
        val permutations = HashSet<ArrayList<String>>()
        getNames(arrayListOf(), permutations)
        val permutationScores = HashSet<Int>()
        for (namePermutation in permutations) {
            var total = 0
            for (i in namePermutation.indices) {
                val nameBefore = if (i == 0) namePermutation.last() else namePermutation[i - 1]
                val nameAfter = if (i == namePermutation.lastIndex) namePermutation.first() else namePermutation[i + 1]
                val currentName = namePermutation[i]
                total += scores[currentName]!![nameAfter]!!
                total += scores[currentName]!![nameBefore]!!
            }
            permutationScores += total
        }
        return permutationScores.max() ?: 0
    }

    private fun getNames(
        currentNames: ArrayList<String>,
        permutations: HashSet<ArrayList<String>>
    ) {
        if (currentNames.toSet().size == names.size) {
            permutations.add(ArrayList(currentNames))
            return
        }

        for (name in names) {
            if (name !in currentNames) {
                currentNames += name
                getNames(currentNames, permutations)
                currentNames.removeAt(currentNames.lastIndex)
            }
        }
    }

    private fun addYourName() {
        val yourName = "Prince"
        names.forEach { currentName ->
            scores[yourName] = scores.getOrDefault(yourName, hashMapOf()).also { map -> map[currentName] = 0 }
            scores[currentName] = scores.getOrDefault(currentName, hashMapOf()).also { map -> map[yourName] = 0 }
        }
        names += yourName
    }
}
