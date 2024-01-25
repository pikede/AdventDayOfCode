package twentythree.dayTwentyTwo

import util.maxOf
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwentyTwo/file.txt"))

fun main() {
    println(part1(input))
    println(part2(input))
}

private data class Brick(
    val x1: Int,
    val y1: Int,
    val z1: Int,
    val x2: Int,
    val y2: Int,
    val z2: Int
) : Comparable<Brick> {
    var supported: List<Brick> = emptyList()
    override fun compareTo(other: Brick) = z1.compareTo(other.z1)
}

private fun Brick.moveDown(distance: Int) = copy(z1 = z1 - distance, z2 = z2 - distance)

private fun Brick.distanceToFall(field: List<IntArray>) =
    z1 - (y1..y2).maxOf { y -> (x1..x2).maxOf { x -> field[y][x] } } - 1

private fun Brick.isSupportedBy(brick: Brick) =
    z1 == brick.z2 + 1 && x1 <= brick.x2 && x2 >= brick.x1 && y1 <= brick.y2 && y2 >= brick.y1

private fun Brick.countReachableWithout(brick: Brick): Int {
    val queue = LinkedList<Brick>().apply { add(this@countReachableWithout) }
    val visited = mutableSetOf(brick)
    var count = 0

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        if (!visited.add(current)) continue
        queue.addAll(current.supported)
        ++count
    }

    return count
}

private operator fun <E> List<E>.component6() = this[5]

private fun parseBrick(line: String) = line.split("~", ",").map { it.toInt() }.let {
    Brick(it[0], it[1], it[2], it[3], it[4], it[5])
}

private fun List<IntArray>.update(brick: Brick) {
    for (y in brick.y1..brick.y2) {
        for (x in brick.x1..brick.x2) {
            this[y][x] = brick.z2
        }
    }
}

private fun List<Brick>.supportingField(): List<IntArray> {
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var maxY = Int.MIN_VALUE

    forEach { brick ->
        minX = minOf(minX, brick.x1)
        minY = minOf(minY, brick.y1)
        maxX = maxOf(maxX, brick.x2)
        maxY = maxOf(maxY, brick.y2)
    }

    return List(maxY + 1) { IntArray(maxX + 1) }
}

private fun List<Brick>.disintegratable() = this - foldRightIndexed(emptyList<List<Brick>>()) { idx, brick, acc ->
    acc + listOf(subList(0, idx).filter(brick::isSupportedBy))
}.filter { it.size == 1 }.flatten().toSet()

private fun part1(input: List<String>): Int {
    val bricks = input.map(::parseBrick).sorted()
    println(bricks)
    val field = bricks.supportingField()

    return bricks.map { it.distanceToFall(field).let(it::moveDown).also(field::update) }
        .sorted()
        .disintegratable()
        .size
}

private fun List<Brick>.appendFieldAsBrick(sizeX: Int, sizeY: Int) = this + Brick(0, 0, 0, sizeX, sizeY, 0)

private val List<Brick>.numOfBricksThatWouldFall: Int
    get() {
        val graph = foldIndexed(mutableMapOf<Brick, List<Brick>>()) { idx, acc, brick ->
            acc[brick] = subList(idx + 1, size).filter { it.isSupportedBy(brick) }
            acc
        }.map { (brick, supported) -> brick.also { it.supported = supported } }
        val field = graph.first()

        return graph.drop(1).sumBy { brick -> graph.lastIndex - field.countReachableWithout(brick) }
    }

private fun part2(input: List<String>): Int {
    val bricks = input.map(::parseBrick).sorted()
    val field = bricks.supportingField()

    return bricks.map { it.distanceToFall(field).let(it::moveDown).also(field::update) }
        .appendFieldAsBrick(field.first().size + 1, field.size + 1)
        .sorted()
        .numOfBricksThatWouldFall
}


