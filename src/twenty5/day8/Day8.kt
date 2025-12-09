package twenty5.day8

import AOCPuzzle
import utils.runningFold
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs
import kotlin.math.pow

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day8/file.txt"))

private fun main() {
    println(Day8.part1())
    println(Day8.part2())
}

private object Day8 : AOCPuzzle {

    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Any {
        val points = quizInput.map { it.split(",").map { it.toInt() } }.map {
            val (x, y, z) = it
            Point3D(x, y, z)
        }
        val distances = (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                points[i].distance(points[j]) to (i to j)
            }
        }.sortedBy { it.first }

        return DSU(points).let { du ->
            distances.take(1000).forEach { (_, p) -> du.union(p.first, p.second) }
            du.clustersSizes().sortedDescending().take(3).fold(1) { acc, v -> acc * v }
        }
    }

    override fun part2(): Any {
        val points = quizInput.map { it.split(",").map { it.toInt() } }.map {
            val (x, y, z) = it
            Point3D(x, y, z)
        }
        val distances = (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                points[i].distance(points[j]) to (i to j)
            }
        }.sortedBy { it.first }

        return DSU(points).let { du ->
            distances.runningFold(Pair(points.size, 0L)) { clusters, (_, p) ->
                val nc = if (du.union(p.first, p.second)) clusters.first - 1 else clusters.first
                Pair(nc, points[p.first].x * points[p.second].x.toLong())
            }.first { it.first == 1 }.second
        }
    }

}

private class DSU(points: List<Point3D>) {
    val root = IntArray(points.size) { it }
    val size = IntArray(points.size) { 1 }

    fun clustersRoots() = root.indices.map { findRoot(it) }.toSet()
    fun clustersSizes() = clustersRoots().map { r -> size[r] }

    fun findRoot(v: Int): Int {
        if (root[v] != v) root[v] = findRoot(root[v])
        return root[v]
    }

    fun union(x: Int, y: Int): Boolean {
        var (rootX, rootY) = findRoot(x) to findRoot(y)

        if (rootX == rootY) return false

        if (size[rootX] < size[rootY]) {
            val temp = rootX; rootX = rootY; rootY = temp
        }

        root[rootY] = rootX
        size[rootX] += size[rootY]
        return true
    }
}


data class Point3D(val x: Int, val y: Int, val z: Int) : Comparable<Point3D> {
    constructor(x: Number, y: Number, z: Number) : this(x.toInt(), y.toInt(), z.toInt())

    val up: Point3D get() = Point3D(x, y, z + 1)
    val down: Point3D get() = Point3D(x, y, z - 1)
    val left: Point3D get() = Point3D(x - 1, y, z)
    val right: Point3D get() = Point3D(x + 1, y, z)
    val forward: Point3D get() = Point3D(x, y + 1, z)
    val backward: Point3D get() = Point3D(x, y - 1, z)

    fun isInside(maxX: Int, maxY: Int, maxZ: Int): Boolean {
        return x in 0 until maxX && y in 0 until maxY && z in 0 until maxZ
    }

    fun getCardinalNeighbors(): Set<Point3D> {
        return setOf(up, down, left, right, forward, backward)
    }

    fun getNeighbors(): List<Point3D> {
        val neighbors = mutableListOf<Point3D>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    if (dx != 0 || dy != 0 || dz != 0) {
                        neighbors.add(Point3D(x + dx, y + dy, z + dz))
                    }
                }
            }
        }
        return neighbors
    }

    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    operator fun plus(other: Int) = Point3D(x + other, y + other, z + other)
    operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)
    operator fun minus(other: Int) = Point3D(x - other, y - other, z - other)
    operator fun times(other: Point3D) = Point3D(x * other.x, y * other.y, z * other.z)
    operator fun times(other: Int) = Point3D(x * other, y * other, z * other)
    operator fun div(other: Int) = Point3D(x / other, y / other, z / other)
    operator fun div(other: Point3D) = Point3D(x / other.x, y / other.y, z / other.z)
    override operator fun compareTo(other: Point3D) = compareValuesBy(this, other, Point3D::x, Point3D::y, Point3D::z)

    infix fun manhattanDistance(other: Point3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    infix fun distance(other: Point3D): Double {
        return abs((x - other.x)).toDouble().pow(2) + abs((y - other.y)).toDouble()
            .pow(2) + abs((z - other.z)).toDouble()
            .pow(2)
    }

    override fun toString() = "$x-$y-$z"

    companion object {
        val ZERO = Point3D(0, 0, 0)
        val ORIGIN = ZERO
        fun from(line: String): Point3D {
            return line.split(",")
                .map { it.toInt() }
                .let {
                    val (x, y, z) = it
                    Point3D(x, y, z)
                }
        }
    }
}