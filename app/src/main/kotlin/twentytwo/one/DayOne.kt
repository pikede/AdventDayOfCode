package twentytwo.one

import org.aoc.utils.readInput
import java.util.*

val input= readInput("src/twentytwo/one/file")

fun main() {
    partOne()  // 69310
    partTwo() // 206104
    partTwoB()
}

fun partOne(): Int {
    var max = input[0].toInt()
    var sum = 0

    input.forEach {
        if (it.equals("")) {
            if (max < sum) {
                max = sum
            }
            sum = 0
        } else {
            sum += it.toInt()
        }

    }

    println(max)
    return max
}

fun partTwo(): Int {
    var pq: Queue<Int> = PriorityQueue { a, b -> b - a }
    var max = input[0].toInt()
    pq.offer(max)
    var sum = 0

    input.forEach {
        if (it.equals("")) {
            if (max < sum) {
                max = sum
                pq.offer(max)
            }
            sum = 0
        } else {
            sum += it.toInt()
        }
    }

    pq.offer(sum)

    println(pq.poll().toInt() + pq.poll().toInt() + pq.poll().toInt())
    return -1
}

fun partTwoB(): Int {
    var max = input[0].toInt()
    var pq = Triple(max, 0, 0)
    var sum = 0

    input.forEach {
        if (it.equals("")) {
            if (max < sum) {
                max = sum
                pq = when {
                    max > pq.first -> Triple(max, pq.first, pq.second)
                    max > pq.second -> pq.copy(second = max, third = pq.second)
                    max > pq.third -> pq.copy(third = max)
                    else -> pq
                }
            }
            sum = 0
        } else {
            sum += it.toInt()
        }
    }



    println(pq.first + pq.second + pq.third)
    return -1
}