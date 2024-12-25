package twenty4.day23

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty4/day23/file.txt"))

private fun main() {
    println(Day23.part1())
    println(Day23.part2())
}

private object Day23 : AOCPuzzle {
    val computers = HashMap<String, MutableList<String>>()
    val threes = HashSet<HashSet<String>>()
    val parties = HashSet<Set<String>>()

    init {
        quizInput.forEach {
            val (start, end) = it.split("-")
            computers[start] = computers.getOrDefault(start, mutableListOf()).also { it.add(end) }
            computers[end] = computers.getOrDefault(end, mutableListOf()).also { it.add(start) }
        }
    }

    override fun part1(): Any {
        groupCommonComputersInTriple()
        var count = 0
        for (i in threes) {
            if (i.any { it.startsWith('t') }) {
                count++
            }
        }
        return count
    }

    private fun groupCommonComputersInTriple() {
        for ((k, v) in computers) {
            for (i in v) {
                val common = computers[i]?.intersect(v.toSet()) ?: continue
                for (m in common) {
                    threes += hashSetOf(k, i, m)
                }
            }
        }
    }

    override fun part2(): Any {
        flattenAllCommonComputers()
        val lanParty = mutableListOf<Set<String>>()
        loop@ for (party in parties) {
            for (k in party) {
                val except = party.toMutableList().filter { it != k }
                if (computers[k]?.containsAll(except) != true) {
                    continue@loop
                }
            }
            lanParty += party
        }
        return lanParty.maxBy { it.size }?.sorted()?.joinToString(",")
            ?: throw IllegalArgumentException("Invalid computers")
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun flattenAllCommonComputers() {
        for ((k, v) in computers) {
            for (i in v) {
                val common = computers[i]?.intersect(v.toSet()) ?: continue
                val temp = buildSet {
                    addAll(common)
                    add(k)
                    add(i)
                }
                parties.add(temp)
            }
        }
    }
}

