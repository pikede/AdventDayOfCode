package yeartwentyone.dayfive

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/dayfive/file.txt")) as ArrayList<String>
    val solution = HydrothermalVenture(input)
    println(solution.partOne())  //  7438
    println(solution.partTwo())  //  21406
}

class HydrothermalVenture(val input: ArrayList<String>) {
    private val allCoordinates = ArrayList<ArrayList<Int>>()
    private val requiredCoordinates = ArrayList<ArrayList<Int>>()
    private val requiredCoordinatesPartTwo = ArrayList<ArrayList<Int>>()

    var xMax = 0
    var yMax = 0

    init {
        getPoints()
    }

    fun partOne(): Int {
        getRequiredPoints()

        val graph = Graph(requiredCoordinates, xMax + 1, yMax + 1)
        graph.plotGraph()

        return graph.getOverlappingPoints()
    }

    fun partTwo(): Int {
        getRequiredPointsPartTwo()

        val graph = Graph(requiredCoordinatesPartTwo, xMax + 1, yMax + 1)
        graph.plotGraphTwo()

        return graph.getOverlappingPoints()
    }

    private fun getPoints() {
        for (i in input) {
//            0,9 -> 5,9 to  0,9,5,9 to [0] [9] [5] [9]
            val line = i.replace(" -> ", ",").split(",")
            val temp = ArrayList<Int>()
            for (i in line) {
                temp.add(Integer.parseInt(i))
            }
            allCoordinates.add(ArrayList(temp))
        }
    }

    /* Gets vertical and horizontal coordinates
    * */
    private fun getRequiredPoints() {
        for (i in allCoordinates) {
            xMax = maxOf(xMax, i[0], i[2])
            yMax = maxOf(yMax, i[1], i[3])
            if (i[0] == i[2] || i[1] == i[3]) {
                requiredCoordinates.add(i)
            }
        }
    }

    /* Gets vertical, horizontal, and diagonal coordinates
    * */
    private fun getRequiredPointsPartTwo() {
        for (i in allCoordinates) {
            xMax = maxOf(xMax, i[0], i[2])
            yMax = maxOf(yMax, i[1], i[3])
            if (i[0] == i[2] || i[1] == i[3]
                || ((i[3] - i[1]) / (i[2] - i[0]) == 1)
                || ((i[3] - i[1]) / (i[2] - i[0]) == -1)
            ) {
                requiredCoordinatesPartTwo.add(i)
            }
        }
    }
}

class Graph(
    private val requiredCoordinates: ArrayList<ArrayList<Int>>,
    rowSize: Int,
    colSize: Int
) {
    private val matrix = Array(rowSize) { Array(colSize) { 0 } }

    fun plotGraph() {
        for (i in requiredCoordinates) {
            when {
                i[0] == i[2] -> {
                    var yMin = minOf(i[1], i[3])
                    var yMax = maxOf(i[1], i[3])
                    while (yMin <= yMax) {
                        matrix[yMin++][i[0]] += 1
                    }
                }
                i[1] == i[3] -> {
                    // increment points at x from min[1] to max[2]  and set to matrix
                    var xMin = minOf(i[0], i[2])
                    var xMax = maxOf(i[0], i[2])

                    while (xMin <= xMax) {
                        matrix[i[3]][xMin++] += 1
                    }
                }
            }
        }
    }

    fun plotGraphTwo() {
        for (i in requiredCoordinates) {
            when {
                i[0] == i[2] -> {
                    var yMin = minOf(i[1], i[3])
                    var yMax = maxOf(i[1], i[3])
                    while (yMin <= yMax) {
                        matrix[yMin++][i[0]] += 1
                    }
                }
                i[1] == i[3] -> {
                    var xMin = minOf(i[0], i[2])
                    var xMax = maxOf(i[0], i[2])
                    while (xMin <= xMax) {
                        matrix[i[3]][xMin++] += 1
                    }
                }
                ((i[3] - i[1]) / (i[2] - i[0]) == 1) -> {
                    // diagonal from left up to right down
                    var x = minOf(i[0], i[2])
                    var max = maxOf(i[0], i[2])
                    var y = minOf(i[1], i[3])
                    while (x <= max) {
                        matrix[y++][x++] += 1
                    }
                }

                ((i[3] - i[1]) / (i[2] - i[0]) == -1) -> {
                    // diagonal from right up to left down
                    var x = maxOf(i[0], i[2])
                    var max = minOf(i[0], i[2])
                    var y = minOf(i[1], i[3])
                    while (x >= max) {
                        matrix[y++][x--] += 1
                    }
                }
            }
        }
    }

    fun getOverlappingPoints(): Int {
        var cnt = 0

        for (row in matrix.indices) {
            for (col in 0 until matrix[row].size) {
                if (matrix[row][col] >= 2) {
                    cnt++
                }
            }
        }
        return cnt
    }
}