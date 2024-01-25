package util

fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return this.first + other.first to this.second + other.second
}

fun Pair<Int, Int>.getUp() = this.first - 1 to this.second
fun Pair<Int, Int>.getDown() = this.first + 1 to this.second
fun Pair<Int, Int>.getLeft() = this.first to this.second - 1
fun Pair<Int, Int>.getRight() = this.first to this.second + 1