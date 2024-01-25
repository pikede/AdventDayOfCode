package twentythree.dayTwentyFour

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwentyFour/file.txt"))

val stones = input.map {
    val temp = it.split(" @ ", ", ").map { it.trim().toDouble() }
    Stone(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5])
}

private fun main() {
    val trajectories = Trajectories(min = 200000000000000.0, max = 400000000000000.0)
    println(trajectories.part1())
}

class Trajectories(val min: Double, val max: Double) {
    fun part1(): Int {
        val intersections = mutableListOf<Pair<Stone, Stone>>()
        for (i in stones.indices) {
            for (j in i + 1..stones.lastIndex) {
                if (intersection(stones[i], stones[j])) {
                    intersections.add(stones[i] to stones[j])
                }
            }
        }
        return intersections.size
    }

    private fun intersection(
        a: Stone,
        b: Stone
    ): Boolean {
        // x = intercept1-intercept2 / slope1-slope2
        // y = m1 * x1 + s1
        // if(x,y) in range of 7..27 true intersection else intersects but somewhere else
        // both a and must cross in future
        val slope = b.slope() - a.slope()
        val yIntercept = a.yIntercept() - b.yIntercept()
        if (slope == 0.0 || b.slope() == a.slope()) {
            return false
        }
        val x = yIntercept / slope
        val y = (a.slope() * x) + a.yIntercept()
        if (!(x in min..max && y in min..max)) {
            return false
        }
        val interceptionAt = x to y
        return a.crossedInTimeFrame(interceptionAt) && b.crossedInTimeFrame(interceptionAt)
    }
}

data class Stone(
    val x: Double,
    val y: Double,
    val z: Double,
    val xVelocity: Double,
    val yVelocity: Double,
    val zVelocity: Double
) {

    fun slope(): Double {
        return yVelocity / xVelocity
    }

    fun yIntercept(): Double {
        val m = slope()
        return y - (m * x)
    }

    fun crossedInTimeFrame(intersectionPoint: Pair<Double, Double>): Boolean {
        val (xi, yi) = intersectionPoint
        val (dx, dy) = xVelocity to yVelocity
        val slope = dy / dx

        return when {
            slope < 0 && dy > 0 -> xi <= x && y <= yi
            slope < 0 && dy < 0 -> x <= xi && yi <= y
            slope > 0 && dy > 0 -> x <= xi && y <= yi
            slope > 0 && dy < 0 -> xi <= x && yi <= y
            else -> true
        }
    }
}