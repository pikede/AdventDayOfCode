package yeartwentyone.dayone

import org.aoc.utils.readInput

fun main() {
    val input = readInput("yeartwentyone/dayone/file")
    println(partOne(input))   // 1548
    println(partTwo(input))   // 1589
}

fun partTwo(input: List<String>): Int {
    var ansCnt = 0
    var index = 0
    val size = input.size
    var previousSum = 0
    while (index < size){
        if (index <= 2){
            previousSum += input[index].toInt()
            index++
            continue
        }
        var nextSum = 0
        var start = index
        var end = 2 + index
//        while(start <= end && end < size){   OR
        while(end in start until size){
            nextSum += input[start++].toInt()
        }
        if (previousSum < nextSum)
            ansCnt++
        previousSum = nextSum
        index++
    }

    return ansCnt
}

fun partOne(input: List<String>): Int {
    var ansCnt = 0
    var index = 0
    val size = input.size

    while (index < size) {
        if (index == 0){
            index++
            continue
        }
        if( input[index].toInt() > input[index - 1 ].toInt()){
            ansCnt++
        }
        index++
    }
    return ansCnt
}