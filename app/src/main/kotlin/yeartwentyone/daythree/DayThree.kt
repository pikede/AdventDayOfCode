package yeartwentyone.daythree

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.pow

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/daythree/file.txt"))
    val p1 = GammaEpsilon(input as ArrayList<String>)
    println(p1.partOne())     // 4118544
    println(p1.partTwo())     // 3832770
}

class GammaEpsilon(var input: ArrayList<String>) {
    fun partOne(): Int {
        var frequency = IntArray(input[0].length) { 0 }

        for (i in input) {
            for (j in i.indices) {
                if (i[j] - '0' == 1) {
                    frequency[j] += 1
                }
            }
        }

        var epsilon = StringBuilder()
        var gamma = StringBuilder()

        for (i in frequency) {
//            highest frequency appears more than half
            gamma.append(if (i > input.size / 2) "1" else "0")
            epsilon.append(if (i > input.size / 2) "0" else "1")
        }

        return gamma.toString().getDecimalValues() * epsilon.toString().getDecimalValues()
    }

    fun partTwo(): Int {
        var i = 0
        var input2 = ArrayList<String>()
        input2.addAll(input)
        val length = input[0].length

        while (i < length && input.size > 1) {
            val  temp = ArrayList<String>()
            var cnt = 0
            val array = IntArray(2)

            while (cnt < input.size) {
                if (input[cnt][i] - '0' == 0) {
                    array[0] += 1
                } else {
                    array[1] += 1
                }
                cnt++
            }

            if (array[0] > array[1]) {
                for (j in input) {
                    if (j[i] - '0' == 0) {
                        temp.add(j)
                    }
                }
            } else {
                for (j in input) {
                    if (j[i] - '0' == 1) {
                        temp.add(j)
                    }
                }
            }
            input.clear()
            input.addAll(temp)
            i++
        }

        var k = 0
        while (k < length && input2.size > 1) {
            var cnt = 0
            val array = IntArray(2)
            val  temp = ArrayList<String>()

            while (cnt < input2.size) {
                if (input2[cnt][k] - '0' == 0) {
                    array[0] += 1
                } else {
                    array[1] += 1
                }
                cnt++
            }

            if (array[1] >= array[0]) {
                for (j in input2) {
                    if (j[k] - '0' == 0) {
                        temp.add(j)
                    }
                }
            } else {
                for (j in input2) {
                    if (j[k] - '0' == 1) {
                        temp.add(j)
                    }
                }
            }
            input2.clear()
            input2.addAll(temp)
            k++
        }

        return input[0].getDecimalValues() * input2[0].getDecimalValues()
    }

    private fun String.getDecimalValues(): Int {
        var i = 0
        var power = this.length - 1
        var decimal = 0
        while (i < this.length) {
            val multiplyBy = 2.0.pow(power).toInt()
            decimal += (this[i] - '0') * multiplyBy
            i++
            power--
        }
        return decimal
    }
}