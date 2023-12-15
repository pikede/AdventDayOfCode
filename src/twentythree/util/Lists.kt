package twentythree.util

fun <T, R, V> Iterable<T>.zipWithIndex(other: R, transform: (Int, Pair<T, R>) -> V): List<V> =
    mapIndexed { index, it -> transform(index, it to other) }

fun <T> Array<List<T>>.printGrid() {
    for (row in this.iterator()) {
        for (column in row) {
            print("$column ")
        }
        println()
    }
}