package twenty5.day6

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput = readInput("twenty5/day6/file")

private fun main() {
    println(Day6.part1())   /// 4693159084994
    println(Day6.part2())  // 11643736116335
}

private object Day6 : AOCPuzzle {
    override fun part1(): Long {
        val numbers = quizInput
            .takeWhile { !it.hasSign() }
            .map {
                it.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
            }
        val signs = quizInput.first { it.hasSign() }.split(" ").filter { it.isNotEmpty() }

        var total = 0L
        signs.forEachIndexed { index, sign ->
            var current = if (sign == "+") 0L else 1L
            when {
                sign == "+" -> numbers.onEach { current += it[index] }
                else -> numbers.onEach { current *= it[index] }
            }
            total += current
        }
        return total
    }

   override fun part2(): Long {
        val splits: List<IntRange> =
            quizInput
                .last()
                .foldIndexed(emptyList<Int>()) { index, acc, s ->
                    when {
                        s == ' ' -> acc
                        else -> acc + maxOf(0, index)
                    }
                }
                .zipWithNext { a, b -> a until b - 1 }
                .let {
                    it + listOf(
                        it.last().last + 2 until quizInput.maxBy(String::length)!!.length,
                    )
                }

        val operations =
            quizInput
                .last()
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::trim)

        // transpose, get all numbers in same column
        // for each row, transpose again but for the digits + pad
        val result =
            quizInput
                .dropLast(1)
                .map { line ->
                    val padded = line.padEnd(splits.last().last + 1, ' ')
                    splits.map { range -> padded.substring(range) }
                }
                .transpose()
                .map {
                    // transpose to get the digits
                    it
                        .transposeString()
                        .map(String::trim)
                        .map(String::toLong)
                }
                .withIndex()

                .sumBy { (index, row) ->
                    when (operations[index]) {
                        "+" -> row.sum()
                        "*" -> row.multiply()
                        else -> error("Unknown operation")
                    }
                }

        return result
    }

    inline fun <T> Iterable<T>.sumBy(selector: (T) -> Long): Long {
        var sum = 0L
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }

    fun List<Long>.multiply(): Long = fold(1L) { acc, n -> acc * n }

    fun <T> List<List<T>>.transpose(): List<List<T>> =
        first().indices.map { i ->
            (indices).map { j ->
                this[j][i]
            }
        }

    fun List<String>.transposeString(): List<String> =
        first().indices.map { i ->
            (indices)
                .map { j -> this[j][i] }
                .joinToString("")
        }

  /*  @OptIn(ExperimentalStdlibApi::class)
    override fun part2(): Long {
        val numbers = quizInput
            .takeWhile { !it.hasSign() }
            .map {
                it.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
            }
        val signs = quizInput.first { it.hasSign() }.split(" ").filter { it.isNotEmpty() }

        var total = 0L
        signs.forEachIndexed { index, sign ->
            var currentTotal = if (sign == "+") 0L else 1L

            when {
                sign == "+" -> {
                    val column = buildList {
                        numbers.forEach { add(it[index].toString().reversed().toInt()) }
                    }
                    val max = column.maxBy { it.toString().length }?.toString()?.length ?: throw Exception("Invalid 1")
                    val array = MutableList(max) { 0 }
                    for (i in column.map { it.toString() }) {
                        var position = max - 1
                        for (j in i.indices.reversed()) {
                            array[position] = (array[position] * 10) + (i[j] - '0')
                            position--
                        }
                    }
                    print(array)
                    array.onEach { currentTotal += it }
                    currentTotal = array.sum().toLong()
                }

                else -> {
                    val column = buildList {
                        numbers.forEach { add(it[index]) }
                    }

                    val max = column.maxBy { it.toString().length }?.toString()?.length ?: throw Exception("Invalid 1")
                    val array = MutableList(max) { 0 }
                    for (i in column.map { it.toString() }) {
                        for ((position, j) in i.indices.withIndex()) {
                            array[position] = (array[position] * 10) + (i[j] - '0')
                        }
                    }
                    array.filter { it > 0 }.onEach { currentTotal *= it }
                }
            }
            println(" ===> $currentTotal   $sign")
            total += currentTotal
        }
        return total
    } // too high 11648318079248
        */

    private fun String.hasSign() = '+' in this || '*' in this
}