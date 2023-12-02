package yeartwentyone.twentyfour

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/twentyfour/file.txt")) as ArrayList<String>


    val comparator = Comparator { a: IntArray, b: IntArray -> a[0] - b[0] }
    val queue : PriorityQueue<IntArray> = PriorityQueue { a: IntArray, b: IntArray -> a[0] - b[0] }
    
    val map = mutableMapOf(
        "w" to 0,
        "x" to 0,
        "y" to 0,
        "z" to 0
    ).withDefault { 0 }


    val set = mutableSetOf<Long>()
    loop@for (j in    99999999998888 downTo 99999988888888) {
        if (j.toString().contains("0") ) {
            continue
        }

        var index = 0
        for (i in input) {
            val instruction = i.split(" ")
            when {
                i.contains("inp") -> {
                    map[instruction[1]] = j.toString()[index] - '0'
                    index++
                }
                i.contains("add") -> {
                    if (map[instruction[2]] != null)
                        map[instruction[1]] = map[instruction[1]]?.plus(map[instruction[2]]!!) ?: 0
                    else
                        map[instruction[1]] = map[instruction[1]]?.plus(instruction[2].toInt()) ?: 0
                }
                i.contains("mod") -> {
                    if (map[instruction[1]]!! > 0 && instruction[2].toInt() > 0)
                        map[instruction[1]] = map[instruction[1]]?.rem(instruction[2].toInt()!!) ?: 0
                }
                i.contains("div") -> {
                    if (instruction[2].toInt() != 0)
                        map[instruction[1]] = map[instruction[1]]?.div(instruction[2].toInt()) ?: 0
                }
                i.contains("mul") -> {
                    if (map[instruction[2]] != null)
                        map[instruction[1]] = map[instruction[1]]?.times(map[instruction[2]]!!) ?: 0
                    else
                        map[instruction[1]] = map[instruction[1]]?.times(instruction[2].toInt()) ?: 0
                }
                i.contains("eql") -> {
                    if (map[instruction[2]] != null)
                        map[instruction[1]] = if (map[instruction[1]] == map[instruction[2]]!!) 1 else 0
                    else
                        map[instruction[1]] = if (map[instruction[1]] == instruction[2].toInt()) 1 else 0
                }
            }
        }
        if (map["z"] == 0) {
            set.add(j)
            break@loop
        }
    }

    println(set)
}