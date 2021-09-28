package daynine

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/daynine/file.txt"))
    println(XMASData(input).getExceptionNumber())   //ANS -> 88311122
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
                    if (inputDataSet[checkerNumber].toInt() + inputDataSet[tempEnd].toInt() == target){
                        seen = true
                    }
                    tempEnd++

                }
                checkerNumber++
            }

            if (!seen){
                return target
            }
        }

        return -1
    }

}

// track start and end of preamble
// loop between start to end to see if the new number is in the preamble range
// if present continue
// else return that number current


// checkerNumber = start
// while (i < end)
// if (target - checkerNumber == current[i]){
// yes continue to next target
// } else {
// return target
// }