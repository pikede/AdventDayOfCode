package yeartwenty.daynine

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwenty/daynine/file.txt"))
    val target = XMASData(input).getExceptionNumber()
    println(target)   //ANS -> 88311122
    println(XMASData(input).getEncryptionWeakness(target))    // ANS  ->  13549369
}

class XMASData(private val inputDataSet: List<String>) {

    var checkerNumber = 0

    fun getExceptionNumber(): Int {
        for (index in inputDataSet.indices) {
            var seen = false
            checkerNumber = index
            val end: Int = index + 24
            val target = inputDataSet[end + 1].toInt()

            while (checkerNumber <= end) {
                var tempEnd = checkerNumber + 1
                while (tempEnd <= end) {
                    // check if checker number + tempend == target
                    if (inputDataSet[checkerNumber].toInt() + inputDataSet[tempEnd].toInt() == target) {
                        seen = true
                    }
                    tempEnd++

                }
                checkerNumber++
            }

            if (!seen) {
                return target
            }
        }

        return -1
    }

    // sliding window range of minimum two numbers
    // check if window sum is == to to target  -> true; return max + min numbers in the window
//                                                 -> else if greater reduce window
//                                                   -> else increase window and keep checking
    fun getEncryptionWeakness(target: Int): Int {
        var start = 0
        var end = 1
        var sum: Long

        while (end < inputDataSet.size) {
            var tempEnd = end
            sum = inputDataSet[start].toLong() + inputDataSet[tempEnd].toLong()
            while (tempEnd < inputDataSet.size && start < tempEnd) {
                when {
                    sum == target.toLong() -> {
                        // check range for largest and smallest and return
                        return getMinMaxSum(start, tempEnd)
                    }
                    sum > target.toLong() -> {
                        // increase window start
                        sum -= inputDataSet[start++].toLong()
                    }
                    else -> {
                        // increase window end
                        tempEnd++
                        sum += inputDataSet[tempEnd].toLong()
                    }
                }
            }
            end = tempEnd
        }
        return -1
    }

    private fun getMinMaxSum(start: Int, end: Int): Int {
        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE
        for (i in start..end) {
            if (min > inputDataSet[i].toInt()) {
                min = inputDataSet[i].toInt()
            }
            if (max < inputDataSet[i].toInt()) {
                max = inputDataSet[i].toInt()
            }
        }

        return min + max
    }

}