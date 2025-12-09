package twenty5.day8

import AOCPuzzle
import utils.Point3D
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


