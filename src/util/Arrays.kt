package util

fun Array<CharArray>.printGrid() {
    for (row in this.iterator()) {
        for (column in row) {
            print("$column ")
        }
        println()
    }
}

fun isValid(row: Int, column: Int, input: Array<CharArray>) =
    row in input.indices && column in input[row].indices

fun isValid(rowToColumn: Pair<Int, Int>, input: Array<CharArray>) =
    isValid(rowToColumn.first, rowToColumn.second, input)

fun getCharArrayGrid(input: MutableList<String>): Array<CharArray> {
    return input.map { it.toCharArray() }.toTypedArray()
}