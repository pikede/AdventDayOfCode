package twentytwo.five

import org.aoc.utils.readInput

val input= readInput("twentytwo/five/file")

fun main() {
    solution()  // A: DHBJQJCCW   B: WJVRLSJJT
}

fun solution() {
    var map = HashMap<Int, MutableList<Char>>()
    var start = 0
    for ((index, element) in input.withIndex()) {
        if (element.count { it.isDigit() } != 0) {
            start = index + 2
            break
        }
        val parsedLine = element.replace("   ", "*").replace("[", "").replace("]", "").replace(" ", "")
        val temp = parsedLine.toMutableList()
        println(temp)
        for (i in temp.indices) {
            val curr = map[i] ?: mutableListOf()
            curr.add(temp[i])
            map[i] = curr
        }
    }
    for (i in map.keys) {
        while (map[i]?.contains('*') == true) {
            val tempSet = map[i] ?: mutableListOf()
            tempSet.remove('*')
            map[i] = tempSet
        }
    }
    while (start in input.indices) {
        val processing = input[start].replace("move ", "").replace(" from ", "*").replace(" to ", "*").split("*")
        val count = processing[0].toInt()
        val from = processing[1].toInt()
        val to = processing[2].toInt()
        if (count == 1) movePositions(count, from - 1, to - 1, map)   // Part A
        else movePositionsB(count, from - 1, to - 1, map)   // Part B
        start++
    }

    for ((key, value) in map) {
        print("${value.first()}")
    }
}


fun movePositionsB(cnt: Int, from: Int, to: Int, map: HashMap<Int, MutableList<Char>>) {
    var temp = '*'
    val curr = mutableListOf<Char>()
    for (i in 0 until cnt) {
        if (map[from]?.isEmpty() == true) {
            return
        }
        temp = map[from]?.first() ?: '*'
        curr.add(temp)
        map[from]?.removeFirst()
    }
    for (i in map[to] ?: mutableListOf()) {
        curr.add(i)
    }
    map[to] = curr

}


fun movePositions(cnt: Int, from: Int, to: Int, map: HashMap<Int, MutableList<Char>>) {
    var temp = '*'

    repeat(cnt) {
        if (map[from]?.isEmpty() == true) {
            return
        }
        temp = map[from]?.first() ?: '*'
        map[to]?.add(0, temp)
        map[from]?.removeFirst()
    }
}
