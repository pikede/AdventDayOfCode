package year2015.day9

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day9/file.txt"))

fun main() {
    val solution = Day9Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day9Solution(val puzzleInput: MutableList<String>) : AOCPuzzle {
    val costs = HashMap<String, HashMap<String, Int>>()
    val paths = ArrayList<Path>()

    @OptIn(ExperimentalStdlibApi::class)
    val allLocations = buildSet {
        puzzleInput.forEach { distances ->
            val (origin, _, destination, _, cost) = distances.split(" ")
            costs[origin] = costs.getOrDefault(origin, HashMap()).also { it[destination] = cost.toInt() }
            costs[destination] = costs.getOrDefault(destination, HashMap()).also { it[origin] = cost.toInt() }
            add(origin)
            add(destination)
        }
    }

    init {
        allLocations.forEach {
            shortestDistanceToVisitAll(it, Path(arrayListOf(it)), paths, allLocations, costs)
        }
    }

    private fun shortestDistanceToVisitAll(
        startCity: String,
        currentPath: Path,
        paths: ArrayList<Path>,
        allLocations: Set<String>,
        costs: HashMap<String, HashMap<String, Int>>
    ) {
        if ((currentPath.visitedLocations.containsAll(allLocations))) {
            paths.add(Path(ArrayList(currentPath.visitedLocations), currentPath.cost))
            return
        }

        for ((city, cost) in costs[startCity] ?: return) {
            if (currentPath.isVisitable(city)) {
                currentPath.addVisitedCity(city)
                currentPath.addCost(cost)
                shortestDistanceToVisitAll(city, currentPath, paths, allLocations, costs)
                currentPath.removeLastVisitedCity()
                currentPath.removeCost(cost)
            }
        }
    }

    override fun part1() = paths.minBy { it.getPathTotalCost() } ?: 0

    override fun part2() = paths.maxBy { it.getPathTotalCost() } ?: 0
}

data class Path(val visitedLocations: ArrayList<String> = arrayListOf(), var cost: Int = 0) {

    fun isVisitable(location: String) = location !in visitedLocations

    fun addCost(cost: Int) {
        this.cost += cost
    }

    fun addVisitedCity(cityName: String) {
        visitedLocations += cityName
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun removeLastVisitedCity() {
        visitedLocations.removeLast()
    }

    fun removeCost(cost: Int) {
        this.cost -= cost
    }

    fun getPathTotalCost() = cost
}