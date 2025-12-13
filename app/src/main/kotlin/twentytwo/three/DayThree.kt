package twentytwo.three

import org.aoc.utils.readInput

private val input= readInput("src/twentytwo/three/file")

fun main() {
    println(partA())  // 8233
    println(partB())  // 2821
}

fun partA(): Int {

    var total = 0

    for (i in input) {
        val mid = i.length / 2
        val first = i.substring(0, mid)
        val second = i.substring(mid)
        for (element in first) {
            if (second.contains(element)) {
                total += getScore(element)
                break
            }
        }
    }
    return total
}

fun partB(): Int {
    var total = 0
    for (i in input.indices step 3) {
        if (i + 2 in input.indices) {
            val first = input[i].toSet()
            val second = input[i + 1].toSet()
            val third = input[i + 2].toSet()
            val element = first.intersect(second).intersect(third)
            total += getScore(element.first())
        } else {
            break
        }
    }

    return total
}

private fun getScore(element: Char): Int {
    return if (element.isLowerCase()) {
        element - 'a' + 1
    } else {
        element - 'A' + 27
    }
}

