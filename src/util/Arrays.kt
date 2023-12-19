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