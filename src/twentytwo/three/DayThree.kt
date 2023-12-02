package twentytwo.three

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/three/file.txt"))

fun main() {
    println(partA())  // 8233
    println(partB())  // 2821
}


//a-z 1-26
//A-Z 27-52
fun partA(): Int {

    var total = 0

    for (i in input) {
        var mid = i.length / 2
        var first = i.substring(0, mid)
        var second = i.substring(mid)

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

            var first = input[i].toSet()
            var second = input[i + 1].toSet()
            var third = input[i + 2].toSet()

            var element = first.intersect(second).intersect(third)
            total += getScore(element.first())
        } else {
            println("broke")
            break
        }
    }

    return total
}

fun getScore(element: Char): Int {
    return if (element.isLowerCase()) {
//        println(element - 'a' + 1)
        element - 'a' + 1
    } else {
//        println(element - 'A' + 27)
        element - 'A' + 27
    }
}

