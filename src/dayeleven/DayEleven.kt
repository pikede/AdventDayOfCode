package dayeleven

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/dayeleven/file.txt"))
    println(SeatingSystem(input).getNumberOfOccupiedSeats())   // ANS 2316
}

val row = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
val col = intArrayOf(1, 1, 1, 0, 0, -1, -1, -1)


class SeatingSystem(private val inputSeatPositions: List<String>) {
    private var seatPositions = arrayListOf<String>()


    fun getNumberOfOccupiedSeats(): Int {
        seatPositions.addAll(inputSeatPositions)
        var tempMap = arrayListOf<String>()

        do {
            if (tempMap.isNotEmpty()) {
                seatPositions.clear()
                seatPositions.addAll(tempMap)
            }

            tempMap.clear()

            for (rowx in seatPositions.indices) {
                var line = ""
                for (coly in seatPositions[rowx].indices) {
                    line += when {
                        seatPositions[rowx][coly] == '#' && getAdjacentOccupied(rowx, coly) > 3 -> {
//                        if 4 or more adjacent seats are occupied, set seat position to empty L
                            'L'
                        }
                        seatPositions[rowx][coly] == 'L' && getAdjacentOccupied(rowx, coly) == 0 -> {
                            // check adjacent
                            // if adjacent seats are empty, occupy this seat
                            '#'
                        }
                        else -> seatPositions[rowx][coly]
                    }
                }
                tempMap.add(line)
            }

        } while (seatPositions != tempMap)

        var cnt = 0
        for (x in seatPositions.indices) {
            for (y in seatPositions[x].indices) {
                if (seatPositions[x][y] == '#') {
                    cnt++
                }
            }
        }

        return cnt
    }

    private fun getAdjacentOccupied(rowx: Int, coly: Int): Int {
        var cntOccupiedAdjacentSits = 0

        for (i in row.indices) {
            val x = rowx + row[i]
            val y = coly + col[i]
            if (isValid(x, y) && seatPositions[x][y] == '#') {
                cntOccupiedAdjacentSits++
            }
        }

        return cntOccupiedAdjacentSits
    }

    private fun isValid(x: Int, y: Int): Boolean {
        return x >= 0 && x < seatPositions.size && y >= 0 && y < seatPositions[0].length
    }

}