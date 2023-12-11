package twentythree.dayEleven

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.collections.HashSet
import kotlin.math.abs

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEleven/file.txt"))


private fun main() {
    val cosmicGrid = CosmicGrid(input)
    println(cosmicGrid.sumGalaxiesShortestPath())
}

class CosmicGrid(private val gridInput: MutableList<String>) {
    private val rowsNeedingGalaxies = ArrayList<Int>()
    private val columnsNeedingGalaxies = ArrayList<Int>()
    private val galaxiesLocation = HashSet<Pair<Int, Int>>()

    init {
        getRowsMissingGalaxies()
        getColumnsMissingGalaxies()
        fillMissingRowGalaxies()
        fillMissingColumnGalaxies()
//        printGrid()
    }

    private fun getRowsMissingGalaxies() {
        for (rowIndex in gridInput.indices) {
            if (!gridInput[rowIndex].contains('#', true)) {
                rowsNeedingGalaxies.add(rowIndex)
            }
        }
//        rowsNeedingGalaxies.sort()
    }

    private fun getColumnsMissingGalaxies() {
        loop@ for (c in gridInput[0].indices) {
            for (r in gridInput.indices) {
                if (gridInput[r][c] == '#') {
                    continue@loop
                }
            }
            columnsNeedingGalaxies.add(c)
        }
//        columnsNeedingGalaxies.sort()
    }

    private fun fillMissingRowGalaxies() {
        val emptyDottedRow = getDottedRow()
        for ((rowsAdded, rowIndex) in rowsNeedingGalaxies.withIndex()) {
            gridInput.add(rowIndex + rowsAdded, emptyDottedRow)
        }
    }

    private fun getDottedRow(): String {
        val rowSize = gridInput[0].length
        val sb = StringBuilder()
        repeat(rowSize) {
            sb.append('.')
        }
        return sb.toString()
    }

    private fun fillMissingColumnGalaxies() {
        for (row in gridInput.indices) {
            for ((count, columnIndex) in columnsNeedingGalaxies.withIndex()) {
                val sb = StringBuilder(gridInput[row])
                sb.insert(columnIndex + count, '.')
                gridInput[row] = sb.toString()
            }
        }
    }

    fun sumGalaxiesShortestPath(): Int {
        findGalaxies()
        var totalSteps = 0
        val galaxies = galaxiesLocation.toList().sortedBy { it.first }
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val steps = getDistanceBetweenGalaxies(galaxies[j], galaxies[i])
                totalSteps += steps
            }
        }
        return totalSteps
    }

    fun sumGalaxiesShortestPathWithExpansion(): Int {
        findGalaxies()
        var totalSteps = 0
        val galaxies = galaxiesLocation.toList().sortedBy { it.first }
        for (i in galaxies.indices) {
            for (j in i + 1 until galaxies.size) {
                val steps = getDistanceBetweenGalaxies(galaxies[j], galaxies[i])
                totalSteps += steps
            }
        }
        return totalSteps
    }

    private fun getDistanceBetweenGalaxies(
        startLocation: Pair<Int, Int>,
        endLocation: Pair<Int, Int>
    ): Int {
        val y = startLocation.second - endLocation.second
        val x = startLocation.first - endLocation.first
        return abs(x) + abs(y)
    }

    private fun findGalaxies() {
        for (r in gridInput.indices) {
            for (c in gridInput[r].indices) {
                if (gridInput[r][c] == '#') {
                    galaxiesLocation.add(r to c)
                }
            }
        }
    }

    private fun printGrid() {
        var galaxyCount = 1
        for (r in gridInput) {
            for (c in r) {
                val value = when {
                    c == '#' -> galaxyCount++
                    else -> '.'
                }
                print(value)
            }
            println()
        }
    }
}
// 9686930
// 9698926
// 9697640 -> to high
// 9696140 -> too high
// 9698222
