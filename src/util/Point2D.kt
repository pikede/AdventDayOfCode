package util

import java.lang.Math.*

data class Point2D(val x: Int, val y: Int) {

    fun up() = applyMove(Move.up)
    fun down() = applyMove(Move.down)
    fun left() = applyMove(Move.left)
    fun right() = applyMove(Move.right)

    fun distanceTo(other: Point2D): Int = abs(x - other.x) + abs(y - other.y)

    fun applyMove(move: Move) = copy(x = x + move.dx, y = y + move.dy)

    operator fun plus(other: Point2D): Point2D {
        return Point2D(x = x + other.x, y = y + other.y)
    }

    fun valueOf(grid: Array<CharArray>) = grid[y][x]
}

data class Move(val dx: Int, val dy: Int) {

    companion object {
        val up = Move(0, -1)
        val down = Move(0, 1)
        val left = Move(-1, 0)
        val right = Move(1, 0)
    }
}