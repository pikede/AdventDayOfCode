package twentythree.dayTwentyThree

import utils.*
import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayTwentyThree/file.txt"))

@OptIn(ExperimentalStdlibApi::class)
val slopes = buildList {
    for (r in input.indices) {
        for (c in input[r].indices) {
            when (input[r][c]) {
                '>', '<', 'v', '^' -> add(r to c)
                else -> {}
            }
        }
    }
}

private fun main() {
    val walker = Walker(input)
    println(walker.getMaxStepsFromStartToEnd())
    println(walker.getMaxStepsFromStartToEndWithoutSlopes())
    println(walker.getMaxStepsFromStartToEndOfSlopes())
}

private class Walker(val input: MutableList<String>) {
    val start = 0 to input[0].indexOf(".")
    val end = input.lastIndex to input.last().indexOf('.')

    fun getMaxStepsFromStartToEnd(): Int {
        val ans = HashSet<MutableList<Pair<Int, Int>>>()
        backtrack(start, ans, arrayListOf())
        return ans.maxBy { it.size }!!.size
    }

    // TODO complete part 2 by fixing this or deleting it
    fun getMaxStepsFromStartToEndOfSlopes(): Int {
        val ans = HashSet<MutableList<Pair<Int, Int>>>()
        backtrack(start, ans, arrayListOf())

        val paths = slopes.associateWith { getSlopePaths(it) }

        var max = 0
        for (i in ans) {
            val temp = i.toHashSet()
            for (k in slopes) {
                if (k in i) {
                    temp += paths[k]!!.first()
                }
            }
            max = maxOf(temp.size, max)
        }

        return max
    }

    fun getSlopePaths(start: Pair<Int, Int>): HashSet<MutableList<Pair<Int, Int>>> {
        val ans = HashSet<MutableList<Pair<Int, Int>>>()
        backtrack(start, ans, arrayListOf())
        return ans
    }

    private fun backtrack(
        current: Pair<Int, Int>,
        ans: HashSet<MutableList<Pair<Int, Int>>>,
        level: ArrayList<Pair<Int, Int>>
    ) {
        if (current == end) {
            ans.add(level.toMutableList())
            return
        }

        for (next in getNext(current)) {
            if (isValid(next, input) && !isForest(next) && next !in level) {
                level += next
                backtrack(next, ans, level)
                level -= next
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getNext(node: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (y, x) = node
        return buildList {
            when (input[y][x]) {
                '^' -> add(node.moveUp())
                '<' -> add(node.moveLeft())
                '>' -> add(node.moveRight())
                'v' -> add(node.moveDown())
                else -> addAll(listOf(node.moveUp(), node.moveLeft(), node.moveRight(), node.moveDown()))
            }
        }
    }

    // TODO complete part 2 by fixing this or deleting it 
    fun getMaxStepsFromStartToEndWithoutSlopes(): Int {
        val points = mutableListOf<Pair<Int, Int>>()
        input.mapIndexed { index, it ->
            it.mapIndexed { i, char ->
                if (char != '#') {
                    points += index to i
                }
            }
        }

        val intersections = points.filter { getNextWithoutSlope(it).size >= 3 }.toSet()
        val cache = HashMap<Pair<Pair<Int, Int>, Pair<Int, Int>>, ArrayList<Int>>()

        val ans = HashSet<Pair<Pair<Int, Int>, Int>>()
        backtrackWithoutSlope(start, intersections, ans, arrayListOf())
        ans.forEach {
            cache[start to it.first] = arrayListOf(it.second)
        }

        intersections.forEach {
            val temp = HashSet<Pair<Pair<Int, Int>, Int>>()
            getNextWithoutSlope(it).forEach { pair ->
                backtrackWithoutSlope(pair, intersections, temp, arrayListOf())
                temp.forEach { (node, size) ->
                    if (node != it && it to node !in cache) {
                        cache[it to node] = cache.getOrDefault(it to node, arrayListOf()).apply {
                            add(size - 1)
                        }
                    }
                }
            }
        }
        return 0
    }

    private fun backtrackWithoutSlope(
        currentNode: Pair<Int, Int>,
        intersections: Set<Pair<Int, Int>>,
        ans: HashSet<Pair<Pair<Int, Int>, Int>>,
        level: ArrayList<Pair<Int, Int>>
    ) {
        if (currentNode in intersections) {
            ans.add(currentNode to level.size)
            return
        }

        for (next in getNextWithoutSlope(currentNode)) {
            if (next !in level) {
                level += next
                backtrackWithoutSlope(next, intersections, ans, level)
                level -= next
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getNextWithoutSlope(node: Pair<Int, Int>): List<Pair<Int, Int>> {
        return buildList {
            add(node.moveUp())
            add(node.moveDown())
            add(node.moveLeft())
            add(node.moveRight())
        }.filter { isValid(it, input) && !isForest(it) }
    }

    private fun isForest(next: Pair<Int, Int>): Boolean {
        return input.getValueOf(next.first, next.second) == '#'
    }
}
// start to intersection
// for every point get next points
// remove walls #
// 3 or more = intersection