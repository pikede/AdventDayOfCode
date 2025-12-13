package twentytwo.four

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/four/file.txt"))

fun main() {
    partA()
    partBNumberOfOverlappingSections()
}


fun partA(): Int {

    var cnt = 0
    for (i in input) {
        var split = i.split(",")
        var first = split[0]
        var second = split[1]

        val splitOne = first.split("-")
        val splitTwo = second.split("-")
        var a = splitOne[0].toInt()
        var b = splitOne[1].toInt()
        var c = splitTwo[0].toInt()
        var d = splitTwo[1].toInt()

        if (c in a..b && d in a..b) {
            cnt++
        } else if (a in c..d && b in c..d) {
            cnt++
        }
    }

    println(cnt)
//    if (first contains all of second) cnt++

    return cnt
}

fun partBNumberOfOverlappingSections(): Int {

    var cnt = 0
    for (i in input) {
        var split = i.split(",")
        var first = split[0]
        var second = split[1]

        val splitOne = first.split("-")
        val splitTwo = second.split("-")
        var a = splitOne[0].toInt()
        var b = splitOne[1].toInt()
        var c = splitTwo[0].toInt()
        var d = splitTwo[1].toInt()

        if (c in a..b || d in a..b) {
            cnt++
        } else if (a in c..d || b in c..d) {
            cnt++
        }
    }

    println(cnt)
//    if (first contains all of second) cnt++

    return cnt
}
