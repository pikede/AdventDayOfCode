package year2015.day19

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day19/file.txt"))
private const val molecule =
    "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"

private fun main() {
    val solution = Day18Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day18Solution : AOCPuzzle {
    @OptIn(ExperimentalStdlibApi::class)
    val replacements = buildMap<String, ArrayList<String>> {
        quizInput.forEach { line ->
            val (origin, destination) = line.split(" => ")
            this[origin] = this.getOrDefault(origin, arrayListOf()).also { it.add(destination) }
        }
    }

    override fun part1(): Any {
        val distinctMolecules = HashSet<String>()
        for (i in molecule.indices) {
            for ((k, replacementValues) in replacements) {
                if (i + k.length > molecule.length) {
                    continue
                }
                val temp = molecule.substring(i, i + k.length)
                if (temp == k) {
                    for (replacement in replacementValues) {
                        val sb = StringBuilder()
                        sb.append(molecule.substring(0, i))
                        sb.append(replacement)
                        sb.append(molecule.substring(i + k.length))
                        distinctMolecules += sb.toString()
                    }
                }
            }
        }
        return distinctMolecules.size
    }

    override fun part2(): Any {
        // counts number of times string occurred 0 base
        val countStr: (String) -> Int = { x -> molecule.split(x).lastIndex }

        // all Symbols upper (only e starting is lower, remove strings that occur on the right but are not on the right i.e Rn, Ar, Y)
        // #NumSymbols - #Rn - #Ar - 2 * #Y - 1
        return molecule.count { it.isUpperCase() } - countStr("Rn") - countStr("Ar") - (2 * countStr("Y")) - 1
    }
}
