package twentythree.dayEleven

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.collections.HashSet
import kotlin.math.abs

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayEleven/file.txt"))

private fun main() {
    val cosmicGrid = CosmicGrid(input)
    println(cosmicGrid.sumGalaxiesShortestPathWithExpansion(1))
    println(cosmicGrid.sumGalaxiesShortestPathWithExpansion(999999))
}

class CosmicGrid(private val gridInput: MutableList<String>) {
    private val rowsNeedingGalaxies = ArrayList<Int>()
    private val columnsNeedingGalaxies = ArrayList<Int>()
    private val galaxiesLocation = HashSet<Pair<Int, Int>>()

    init {
        getRowsMissingGalaxies()
        getColumnsMissingGalaxies()
    }

    private fun getRowsMissingGalaxies() {
        for (rowIndex in gridInput.indices) {
            if (!gridInput[rowIndex].contains('#', true)) {
                rowsNeedingGalaxies.add(rowIndex)
            }
        }
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
    }

    fun sumGalaxiesShortestPathWithExpansion(delta: Long): Long {
        findGalaxies()
        var totalSteps = 0L
        val adjustedGalaxies = adjustGalaxiesLocation(galaxiesLocation, delta)
        for (i in adjustedGalaxies.indices) {
            for (j in i + 1 until adjustedGalaxies.size) {
                val steps = getDistanceBetweenGalaxies(adjustedGalaxies[j], adjustedGalaxies[i])
                totalSteps += steps
            }
        }
        return totalSteps
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

    private fun adjustGalaxiesLocation(galaxies: HashSet<Pair<Int, Int>>, delta: Long): List<Pair<Long, Long>> {
        val newGalaxies = arrayListOf<Pair<Long, Long>>()
        for (galaxy in galaxies) {
            val newRow = getAdjustedRowIndex(galaxy.first, delta)
            val newColumn = getAdjustedColumnIndex(galaxy.second, delta)
            newGalaxies.add(newRow to newColumn)
        }
        return newGalaxies
    }

    private fun getAdjustedRowIndex(rowIndex: Int, delta: Long): Long {
        var count = 0
        for (r in rowsNeedingGalaxies) {
            if (rowIndex >= r) {
                count++
            }
        }
        return rowIndex + (delta * count)
    }

    private fun getAdjustedColumnIndex(columnIndex: Int, delta: Long): Long {
        var count = 0
        for (column in columnsNeedingGalaxies) {
            if (columnIndex >= column) {
                count++
            }
        }
        return columnIndex + (delta * count)
    }

    private fun getDistanceBetweenGalaxies(
        startLocation: Pair<Long, Long>,
        endLocation: Pair<Long, Long>
    ): Long {
        val y = startLocation.second - endLocation.second
        val x = startLocation.first - endLocation.first
        return abs(x) + abs(y)
    }
}
