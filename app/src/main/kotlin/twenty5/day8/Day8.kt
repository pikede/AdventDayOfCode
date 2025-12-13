package twenty5.day8

import AOCPuzzle
import org.aoc.utils.readInput
import utils.Point3D
import utils.runningFold

private val quizInput: List<String> = readInput("twenty5/day8/file")

private fun main() {
    println(Day8.part1())
    println(Day8.part1V1())
    println(Day8.part2())
}

private object Day8 : AOCPuzzle {

    val points = quizInput.map { it.split(",").map { it.toInt() } }.map {
        val (x, y, z) = it
        Point3D(x, y, z)
    }
    val distances = (0..points.lastIndex).flatMap { i ->
        (i + 1..points.lastIndex).map { j ->
            points[i].distance(points[j]) to (i to j)
        }
    }.sortedBy { it.first }

    @OptIn(ExperimentalStdlibApi::class)
    fun part1V1(): Any {
        return DSU(points).let { du ->
            distances.take(1000).forEach { (_, p) -> du.union(p.first, p.second) }
            du.clustersSizes().sortedDescending().take(3).fold(1) { acc, v -> acc * v }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Any {
        val n = 1000
        val distances = (0..points.lastIndex).flatMap { i ->
            (i + 1..points.lastIndex).map { j ->
                points[i].distance(points[j]) to (points[i] to points[j])
            }
        }.sortedBy { it.first }.take(n)

        val compressor = mutableMapOf<Point3D, MutableList<Point3D>>()

        for (circuits in distances) {
            val (a, b) = circuits.second
            compressor[a] = compressor.getOrDefault(a, mutableListOf()).also { it.add(b) }
            compressor[b] = compressor.getOrDefault(b, mutableListOf()).also { it.add(a) }
        }

        val result = mutableListOf<List<Point3D>>()
        val visited = mutableSetOf<Point3D>()
        for (point in compressor.keys) {
            if (point in visited) continue
            val level = mutableListOf<Point3D>()
            flatten(level, visited, point, compressor)
            result += level
        }
        return result.sortedByDescending { it.size }.take(3).fold(1) { acc, points -> acc * points.size }
    }

    private fun flatten(
        level: MutableList<Point3D>,
        visited: MutableSet<Point3D>,
        point: Point3D,
        compressor: MutableMap<Point3D, MutableList<Point3D>>
    ) {
        if (point in visited) return
        level += point
        visited += point
        compressor[point]?.forEach { next ->
            flatten(level, visited, next, compressor)
        }
    }

    override fun part2(): Any {
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


