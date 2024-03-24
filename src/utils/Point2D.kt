package utils

import utils.Move.Companion.down
import utils.Move.Companion.left
import utils.Move.Companion.right
import utils.Move.Companion.up
import java.lang.Math.*

data class Point2D(val x: Int, val y: Int) {

    fun up() = applyMove(up)
    fun down() = applyMove(down)
    fun left() = applyMove(left)
    fun right() = applyMove(right)

    fun distanceTo(other: Point2D): Int = abs(x - other.x) + abs(y - other.y)

    fun applyMove(move: Move) = copy(x = x + move.dx, y = y + move.dy)

    operator fun plus(other: Point2D): Point2D {
        return Point2D(x = x + other.x, y = y + other.y)
    }

    fun valueOf(grid: Array<CharArray>) = grid[y][x]
}

fun move(move: Move, rowIndex: Int, columnIndex: Int): Pair<Int, Int> {
    return when (move) {
        up, down, left, right -> move.dy + rowIndex to move.dx + columnIndex
        else -> throw IllegalArgumentException("Invalid move direction")
    }
}

fun Pair<Int, Int>.move(move: Move): Pair<Int, Int> {
    return when (move) {
        up, down, left, right -> move.dy + first to move.dx + second
        else -> throw IllegalArgumentException("Invalid move direction")
    }
}

data class Move(val dx: Int, val dy: Int) {
    companion object {
        val up = Move(0, -1)
        val down = Move(0, 1)
        val left = Move(-1, 0)
        val right = Move(1, 0)
    }
}

fun Char.getMoveByArrowDirection(): Move {
    return when (this) {
        '^' -> up
        '<' -> left
        '>' -> right
        'v' -> down
        else -> throw Exception("Invalid Direction")
    }
}