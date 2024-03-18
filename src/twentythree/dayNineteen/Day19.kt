package twentythree.dayNineteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayNineteen/file.txt"))

private fun main() {
    val calculator = WorkflowCalculator(input)
    println(calculator.part1())
}

typealias partList = ArrayList<Part>

private class WorkflowCalculator(val input: MutableList<String>) {
    private val workflows = HashMap<String, Workflow>()
    private val parts = partList()

    init {
        parseInput()
    }

    private fun parseInput() {
        val partsStartIndex = input.indexOf("")
        input.forEachIndexed { index, s ->
            if(s.isNotEmpty()){
                if (index < partsStartIndex) {
                    addWorkflow(s)
                } else {
                    addPartRanges(s)
                }
            }
        }
    }

    private fun addWorkflow(s: String) {
        val (Workflow, instruction) = s.replace("}", "").split("{")
        val rules = instruction.split(",").toMutableList()
        workflows[Workflow] = Workflow(rules)
    }

    private fun addPartRanges(s: String) {
        val part = mutableMapOf<Char, Int>()
        s.replace("{", "").replace("}", "").split(",").forEach {
            val (name, value) = it.split("=")
            part[name.first()] = Integer.parseInt(value)
        }
        parts.add(Part(part))
    }

    fun part1(): Int {
        val startRule = workflows["in"]!!.rules
        return parts.fold(0) { acc, part -> acc + runWorkflow(part, startRule) }
    }

    private fun runWorkflow(part: Part, start: MutableList<String>): Int {
        for (i in start) {
            when (i) {
                "A" -> return part.partSum()
                "R" -> return 0
                else -> {
                    when {
                        !i.contains('>') && !i.contains('<') -> {
                            return runWorkflow(part, workflows[i]!!.rules)
                        }
                        else -> {
                            val (partName, criteria) = i.split(">", "<")
                            val (amount, instruction) = criteria.split(":")
                            val amountIntValue = Integer.parseInt(amount)
                            val partAmount = part.getPartAmount(partName.first())!!
                            val rating = getRating(instruction, part) {
                                runWorkflow(
                                    part,
                                    workflows[instruction]!!.rules
                                )
                            }
                            val isLessThan = isLessThan(i, partAmount, amountIntValue)
                            val isGreaterThan = isGreaterThan(i, partAmount, amountIntValue)
                            if (isLessThan || isGreaterThan) {
                                return rating
                            }
                        }
                    }
                }
            }
        }
        return 0
    }

    private fun isLessThan(i: String, partAmount: Int, amount: Int) = i.contains("<") && partAmount < amount

    private fun isGreaterThan(i: String, partAmount: Int, amount: Int) = i.contains(">") && partAmount > amount

    private fun getRating(instruction: String, part: Part, continueWorkflow: () -> Int) = when (instruction) {
        Rating.Accepted.type -> part.partSum()
        Rating.Rejected.type -> 0
        else -> continueWorkflow()
    }
}

data class Workflow(val rules: MutableList<String>)

data class Part(val map: MutableMap<Char, Int>) {

    fun getPartAmount(name: Char) = map[name]

    fun partSum() = map.values.sum()
}

enum class Rating(val type: String) {
    Accepted("A"),
    Rejected("R");
}