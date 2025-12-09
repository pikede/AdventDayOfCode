package utils

fun <T, R, V> Iterable<T>.zipWithIndex(other: R, transform: (Int, Pair<T, R>) -> V): List<V> =
    mapIndexed { index, it -> transform(index, it to other) }

fun <T> Array<ArrayList<T>>.printGrid() {
    for (row in this.iterator()) {
        for (column in row) {
            print("$column ")
        }
        println()
    }
}

fun isValid(row: Int, column: Int, input: MutableList<String>) =
    row in input.indices && column in input[row].indices

fun isValid(rowToColumn: Pair<Int, Int>, input: MutableList<String>) =
    isValid(rowToColumn.first, rowToColumn.second, input)

fun MutableList<String>.intValueOf(row: Int, column: Int) = this[row][column] - '0'
fun MutableList<String>.getValueOf(row: Int, column: Int) = this[row][column]

public inline fun <T, R> Iterable<T>.runningFold(initial: R, operation: (acc: R, T) -> R): List<R> {
    val estimatedSize = 9
    if (estimatedSize == 0) return listOf(initial)
    val result = ArrayList<R>(estimatedSize + 1).apply { add(initial) }
    var accumulator = initial
    for (element in this) {
        accumulator = operation(accumulator, element)
        result.add(accumulator)
    }
    return result
}
