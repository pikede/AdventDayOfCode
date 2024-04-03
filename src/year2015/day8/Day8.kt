package year2015.day8

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day8/file.txt"))

fun main() {
    val solution = Day8Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day8Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        var codeTotal = 0
        var inMemoryTotal = 0

        puzzleInput.forEach {
            codeTotal += it.length
            inMemoryTotal += it.getValueInMemory()
        }
        return codeTotal - inMemoryTotal
    }

    private fun String.getValueInMemory(): Int {
        val stack = Stack<Char>()
        var i = 0

        while (i in this.indices) {
            while (i in this.indices && this[i] == '"') {
                i++
            }
            if (i in this.indices && this[i] == '\\') {
                i++
                when (this[i]) {
                    '"', '\\' -> {
                        stack.push(this[i])
                        i++
                    }
                    'x' -> {
                        stack.push(this[i])
                        i += 3
                    }
                }
            } else {
                if (i in this.indices) {
                    stack.push(this[i])
                    i++
                }
            }
        }
        return stack.size
    }

    override fun part2(): Any {
        var codeTotal = 0
        var encodedLength = 0

        puzzleInput.forEach {
            codeTotal += it.length
            encodedLength += it.getEncodedLength()
        }
        return encodedLength - codeTotal
    }

    private fun String.getEncodedLength(): Int {
        val stack = Stack<Char>()
        var i = 0
        stack.push('"')
        while (i in this.indices) {
            while (i in this.indices && this[i] == '"') {
                stack.push('\\')
                stack.push(this[i])
                i++
            }
            if (i in this.indices && this[i] == '\\') {
                stack.push('\\')
                stack.push('\\')
                i++
                when (this[i]) {
                    '"', '\\' -> {
                        stack.push('\\')
                        stack.push(this[i])
                        i++
                    }
                    'x' -> {
                        stack.push(this[i])
                        stack.push(this[i + 1])
                        stack.push(this[i + 2])
                        i += 3
                    }
                }
            } else {
                if (i in this.indices) {
                    stack.push(this[i])
                    i++
                }
            }
        }
        stack.push('"')
        return stack.size
    }
}
