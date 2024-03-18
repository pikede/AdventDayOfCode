package utils

infix operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    this.first + other.first to this.second + other.second

fun Pair<Int, Int>.moveUp() = this.move(Move.up)
fun Pair<Int, Int>.moveDown() = this.move(Move.down)
fun Pair<Int, Int>.moveLeft() = this.move(Move.left)
fun Pair<Int, Int>.moveRight() = this.move(Move.right)