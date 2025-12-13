package year2015.day7

import AOCPuzzle
import org.aoc.utils.readInput
import java.util.function.IntBinaryOperator

private val questionInput= readInput("src/year2015/day7/file").toMutableList()

fun main() {
    val solution = Day7Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private val circuitWires = HashMap<String, Element>()
private val operators = mapOf(
    "AND" to IntBinaryOperator { a: Int, b: Int -> a and b },
    "OR" to IntBinaryOperator { a: Int, b: Int -> a or b },
    "LSHIFT" to IntBinaryOperator { a: Int, b: Int -> a shl b },
    "RSHIFT" to IntBinaryOperator { a: Int, b: Int -> a shr b }
)

private class Day7Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {

    override fun part1(): Any {
        runInstructions()
        return circuitWires["a"]!!.getValue()
    }

    override fun part2(): Any {
        runInstructions()
        circuitWires["b"] = Literal(956)
        return circuitWires["a"]!!.getValue()
    }

    private fun runInstructions() {
        puzzleInput.forEach {
            val split = it.split(" ")
            when {
                split.size == 3 -> {
                    val node = Node(split[0].getElement())
                    circuitWires[split[2]] = node
                }
                split[0] == "NOT" -> {
                    val node = Node(Negation(split[1].getElement()))
                    circuitWires[split[3]] = node
                }
                else -> {
                    val node = Node(Operator(split[1], split[0].getElement(), split[2].getElement()))
                    circuitWires[split[4]] = node
                }
            }
        }
    }

    private fun String.getElement(): Element {
        return if (this.matches("\\d+".toRegex())) {
            Literal(Integer.parseInt(this))
        } else {
            LazyNode(this)
        }
    }
}

private interface Element {
    fun getValue(): Int
}

private class Node(val value: Element) : Element {
    var cached: Int? = null

    override fun getValue(): Int {
        if (cached == null) {
            cached = value.getValue() and 0xffff
        }
        return cached!!
    }
}

private class LazyNode(val name: String) : Element {
    override fun getValue(): Int {
        return circuitWires[name]!!.getValue()
    }
}

private class Literal(val numberValue: Int) : Element {
    override fun getValue() = numberValue
}

private class Negation(val original: Element) : Element {
    override fun getValue(): Int {
        return original.getValue().inv()
    }
}

private class Operator(title: String, val left: Element, val right: Element) : Element {
    private val operator = operators[title]!!
    override fun getValue() = operator.applyAsInt(left.getValue(), right.getValue())
}
