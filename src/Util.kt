
fun printMatrix(matrix: Array<CharArray>) {
    for (row in matrix.indices) {
        for (column in matrix[row].indices) {
            print("${matrix[row][column]}, ")
        }
        println()
    }
    println()
}