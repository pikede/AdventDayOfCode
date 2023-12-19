package twentythree.dayNineteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayNineteen/file.txt"))

private fun main() {
    val calculator = WorkFlowCalculator(input)
    println(calculator.runInstruction())
}

private class WorkFlowCalculator(val input: MutableList<String>) {
    val workFlows = HashMap<String, WorkFlow>()
    val parts = mutableListOf<Part>()

    init {
        parseInput()
    }

    private fun parseInput() {
        var partsStartIndex = input.indexOf("")
        input.forEachIndexed { index, s ->
            when {
                s.isEmpty() -> {}
                else -> {
                    if (index < partsStartIndex) {
                        addWorkFlow(s)
                    } else {
                        addPart(s)
                    }
                }
            }
        }
    }

    private fun addWorkFlow(s: String) {
        val (workFlow, instruction) = s.replace("}", "").split("{")
        val rules = instruction.split(",").toMutableList()
        workFlows[workFlow] = WorkFlow(rules)
    }

    private fun addPart(s: String) {
        val part = mutableMapOf<String, Int>()
        s.replace("{", "").replace("}", "").split(",").forEach {
            val (name, value) = it.split("=")
            part[name] = Integer.parseInt(value)
        }
        parts.add(Part(part))
    }

    fun runInstruction(): Int {
        var total = 0
        for (part in parts) {
            total += runWorkflow(part, workFlows["in"]!!.rules)
        }
        return total
    }

    private fun runWorkflow(part: Part, start: MutableList<String>): Int {
        for (i in start) {
            when {
                i == "A" -> return part.partSum()
                i == "R" -> return 0
                i.contains("<") -> {
                    val (partName, criteria) = i.split("<")
                    val (amount, instruction) = criteria.split(":")
                    val amountInt = Integer.parseInt(amount)
                    val partAmount = part.getPartAmount(partName) ?: continue
                    if (partAmount < amountInt) {
                        return when (instruction) {
                            "A" -> {
                                part.partSum()
                            }
                            "R" -> {
                                0
                            }
                            else -> {
                                runWorkflow(part, workFlows[instruction]!!.rules)
                            }
                        }
                    }
                }
                i.contains(">") -> {
                    val (partName, criteria) = i.split(">")
                    val (amount, instruction) = criteria.split(":")
                    val amountInt = Integer.parseInt(amount)
                    val partAmount = part.getPartAmount(partName) ?: continue
                    if (partAmount > amountInt) {
                        return when (instruction) {
                            "A" -> {
                                part.partSum()
                            }
                            "R" -> {
                                0
                            }
                            else -> {
                                runWorkflow(part, workFlows[instruction]!!.rules)
                            }
                        }
                    }
                }
                else -> {
                    return runWorkflow(part, workFlows[i]!!.rules)
                }
            }
        }
        return 0
    }
}

private data class WorkFlow(val rules: MutableList<String>)

private data class Part(val map: MutableMap<String, Int>) {

    fun getPartAmount(name: String) = map[name]

    fun partSum() = map.values.sum()
}
// TODO create work flow parser
// Todo create parts parser
