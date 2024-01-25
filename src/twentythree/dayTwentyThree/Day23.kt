package twentythree.dayTwentyThree

import util.*
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
//    println(walker.getMaxStepsFromStartToEndWithoutSlopes())
    println(walker.getMaxStepsFromStartToEndOfSlopes())
}

private class Walker(val input: MutableList<String>) {
    val start = 0 to input[0].indexOf(".")
    val end = input.lastIndex to input.last().indexOf('.')
    val allSteps = HashSet<Pair<Int, Int>>()


    fun getMaxStepsFromStartToEnd(): Int {
        val ans = HashSet<MutableList<Pair<Int, Int>>>()
        backtrack(start, ans, arrayListOf())
        return ans.maxBy { it.size }!!.size
    }

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
            printGrid(temp)
            println(max)
            println()
        }

        return max
//        return ans.maxBy { it.size }!!.size
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

    private fun getNext(node: Pair<Int, Int>): List<Pair<Int, Int>> {
        val up = node.getUp()
        val down = node.getDown()
        val left = node.getLeft()
        val right = node.getRight()
        val (y, x) = node
        return when (input[y][x]) {
            '^' -> listOf(up)
            '<' -> listOf(left)
            '>' -> listOf(right)
            'v' -> listOf(down)
            else -> listOf(up, left, right, down)
        }
    }

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
                        cache[it to node] = cache.getOrDefault(it to node, arrayListOf()).also {
                            it.add(size - 1)
                        }
                    }
                }
            }
        }
        println(cache)
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

    private fun getNextWithoutSlope(node: Pair<Int, Int>): List<Pair<Int, Int>> {
        val up = node.getUp()
        val down = node.getDown()
        val left = node.getLeft()
        val right = node.getRight()
        return listOf(up, left, right, down).filter { isValid(it, input) }.filter { !isForest(it) }
    }

    private fun isForest(next: Pair<Int, Int>): Boolean {
        return input.getValueOf(next.first, next.second) == '#'
    }

    fun printGrid() {
        for (r in input.indices) {
            for (c in input[r].indices) {
                val temp = when {
                    r to c in allSteps -> 'O'
                    else -> input[r][c]
                }
                print("$temp ")
            }
            println()
        }
    }

    fun printGrid(temp: HashSet<Pair<Int, Int>>) {
        for (r in input.indices) {
            for (c in input[r].indices) {
                val i = when {
                    r to c in temp -> 'O'
                    else -> input[r][c]
                }
                print("$i ")
            }
            println()
        }
    }
}
// start to intersection
// for every point get next points
// remove walls #
// 3 or more = intersection