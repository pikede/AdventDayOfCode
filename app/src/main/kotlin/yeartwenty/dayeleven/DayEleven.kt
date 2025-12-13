package yeartwenty.dayeleven

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwenty/dayeleven/file")
    println(SeatingSystem(input).getNumberOfOccupiedSeats())   // ANS 2316
    println(SeatingSystem(input).partTwo())     // ANS 2128
}

private val row = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
private val col = intArrayOf(1, 1, 1, 0, 0, -1, -1, -1)

class SeatingSystem(private val inputSeatPositions: List<String>) {
    private var seatPositions = arrayListOf<String>()

    fun getNumberOfOccupiedSeats(): Int {
        seatPositions.addAll(inputSeatPositions)
        val tempMap = arrayListOf<String>()

        do {
            if (tempMap.isNotEmpty()) {
                seatPositions.clear()
                seatPositions.addAll(tempMap)
            }

            tempMap.clear()

            for (rowx in seatPositions.indices) {
                var line = ""
                for (coly in seatPositions[rowx].indices) {
                    val numberAdjacent : Int = getAdjacentOccupied(rowx, coly)
                    line += when {
                        seatPositions[rowx][coly] == '#' && numberAdjacent > 3 -> {
//                        if 4 or more adjacent seats are occupied, set seat position to empty L
                            'L'
                        }
                        seatPositions[rowx][coly] == 'L' && numberAdjacent == 0 -> {
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

    fun partTwo(): Int {
        var ans = 0

        seatPositions.addAll(inputSeatPositions)
        val tempMap = arrayListOf<String>()

        do {
            if (tempMap.isNotEmpty()) {
                seatPositions.clear()
                seatPositions.addAll(tempMap)
            }

            tempMap.clear()

            for (rowX in seatPositions.indices) {
                var line = ""
                for (colY in seatPositions[rowX].indices) {
                    line += when {
                        seatPositions[rowX][colY] == '#' && getAdjacentOccupiedPartTwo(rowX, colY) > 4 -> {
//                        if 4 or more adjacent seats are occupied, set seat position to empty L
                            'L'
                        }
                        seatPositions[rowX][colY] == 'L' && getAdjacentOccupiedPartTwo(rowX, colY) == 0 -> {
                            // check adjacent
                            // if adjacent seats are empty, occupy this seat
                            '#'
                        }
                        else -> seatPositions[rowX][colY]
                    }
                }
                tempMap.add(line)
            }

        } while (seatPositions != tempMap)

        for (x in seatPositions.indices) {
            for (y in seatPositions[x].indices) {
                if (seatPositions[x][y] == '#') {
                    ans++
                }
            }
        }

        return ans
    }

    private fun getAdjacentOccupiedPartTwo(rowX: Int, colY: Int): Int {
        var cntOccupiedAdjacentSits = 0

        for (i in row.indices) {
            var x = rowX + row[i]
            var y = colY + col[i]
            while (isValid(x, y) && seatPositions[x][y] == '.') {
                x +=  row[i]
                y +=  col[i]
            }
            if (isValid(x, y) && seatPositions[x][y] == '#') {
                cntOccupiedAdjacentSits++
            }
        }

        return cntOccupiedAdjacentSits
    }

    private fun isValid(x: Int, y: Int): Boolean {
        return x >= 0 && x < seatPositions.size && y >= 0 && y < seatPositions[x].length
    }
}