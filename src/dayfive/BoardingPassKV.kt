package dayfive

import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.floor
import kotlin.math.pow

fun main() {
    val textFile = Files.readAllLines(Paths.get("src/dayfive/file.txt"))
    println(getMaxBoardingPassSeatId(textFile))    // ANS:  883
    println(getMaxBoardingPassSeatIdVariationTwo(textFile))    // ANS:  883
    println(getMissingBoardingSeatPartTwo(textFile))    // ANS:  532
}

private fun getMaxBoardingPassSeatId(boardingPasses: List<String>): Int {
    var maxSeatId = Int.MIN_VALUE
    val lastRow = 7
    val lastColumnIndex = 9

    for (boardingPass in boardingPasses) {
        var currentRowMax = 0

        // ROW SECTION
        val rowArr: IntArray = intArrayOf(0, 127)
        for (row in 0 until lastRow) {
            if (boardingPass[row] == 'F') {
                // keep lower(front), move higher index
                val median = getMedian(rowArr[0], rowArr[1], 'F')
                // median + rowArr[0]=> if median is less than lower bound, adjust (by adding lower to median)
                rowArr[1] = median.coerceAtLeast(median + rowArr[0])
//                rowArr[1] = Math.max(median, median + rowArr[0]) same as above
                // coerceAtLeast=> ensures that median is not less than median + rowArr[0].
                // if median < median + rowArr[0] return median < rowArr[0]
                // else return median
            } else {
                // keep higher(back), move lower index
                rowArr[0] += getMedian(rowArr[0], rowArr[1], 'B')
            }
        }
        currentRowMax = if (boardingPass[6] == 'F') rowArr[0] else rowArr[1]

        // COLUMN SECTION
        val columnArr: IntArray = intArrayOf(0, 7)
        for (column in 7..lastColumnIndex) {
            if (boardingPass[column] == 'L') {
                // keep lower(left), move higher index
                val median = getMedian(columnArr[0], columnArr[1], 'L')
                // median + colArr[0]=> if median is less than lower bound, adjust (by adding lower to median)
                columnArr[1] = median.coerceAtLeast(median + columnArr[0])
                // coerceAtLeast=> ensures that median is not less than median + colArr[0].
                // if median < median + colArr[0] return median < colArr[0]
                // else return median
            } else {
                // keep higher, move lower index
                columnArr[0] += getMedian(columnArr[0], columnArr[1], 'R')
            }
            //  select lower/upper (L/R) value from the boarding pass
            val currentColumnId = if (boardingPass[lastColumnIndex] == 'L') columnArr[0] else columnArr[1]
            maxSeatId = (currentRowMax * 8 + currentColumnId).coerceAtLeast(maxSeatId)
//            maxSeatId = Math.max(currentRowMax * 8 + currentColumnId, maxSeatId) // same as  above
        }
    }

    return maxSeatId
}

/*
 * @param low  -> low limit
 * @param high -> high limit
 * @param zoneChar -> sitting zoneChar,
 * F -> Front,
 * B -> Back (must be rounded up),
 * L -> Left,
 * R -> Right (must be rounded up)
 */
private fun getMedian(low: Int, high: Int, zoneChar: Char): Int {
    val n = high - low
    val median = if (n % 2 == 0) {
        (floor((n / 2 + n / 2 - 1).toDouble()) / 2).toInt()
    } else {
        n / 2
    }

    return if (zoneChar == 'F' || zoneChar == 'L') {
        // no rounding needed, as lower half selected from division above
        median
    } else {
        median + 1
    }
}

private fun getMaxBoardingPassSeatIdVariationTwo(boardingPasses: List<String>): Int {
    var maxSeatId = Int.MIN_VALUE
    for (boardingPass in boardingPasses) {
        val binary = StringBuilder()
        boardingPass.forEach {
            binary.append(when (it) {
                'F' -> "0"
                'B' -> "1"
                'L' -> "0"
                'R' -> "1"
                else -> ""
            })
        }
        val currentSeatId = getDecimalValue(binary.toString())
        maxSeatId = maxSeatId.coerceAtLeast(currentSeatId)
    }

    return maxSeatId
}

private fun getMissingBoardingSeatPartTwo(boardingPasses: List<String>): Int {
    val integerSeatId = mutableListOf<Int>()
    //  store integer representation for all seatIds
    for (id in boardingPasses) {
        val binary = StringBuilder()
        id.forEach {
            binary.append(when (it) {
                'F' -> "0"
                'B' -> "1"
                'L' -> "0"
                'R' -> "1"
                else -> ""
            })
        }
        integerSeatId.add(getDecimalValue(binary.toString()))
    }

    integerSeatId.sort()
    var currentSeatId = integerSeatId[0]  // // start search  with the smallest seat id
    // search for missing seat id
    for (id in integerSeatId) {
        if (currentSeatId != id) {  // check if id is missing
            return currentSeatId   // return missing id
        }
        currentSeatId++
    }

    return currentSeatId
}

private fun getDecimalValue(binary: String): Int {
    var decimalValue = 0.0
    var power = 0.0
    for (index in binary.length - 1 downTo 0) {
        decimalValue += Character.getNumericValue(binary[index]) * 2.0.pow(power)
        power++
    }
    return decimalValue.toInt()
}