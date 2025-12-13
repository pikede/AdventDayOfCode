package twentytwo.two

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/two/file.txt"))

fun main() {
    println(input)
    partA()  // 9759
    partB()  // 12429
}

//A X
//B Y
//C Z

//score  =  (1 Rock, 2 Paper, 3 Scissors)  + (0  lost, 3 draw, 6 won)
fun partA(): Int {
    var totalScore = 0
//    W L D
//    X  ->   Z   Y   X
//    Y  ->   X   Z   Y
//    Z  ->   Y   X   Z

    val map = mutableMapOf<Char, ArrayList<Char>>()
    map['X'] = arrayListOf('Z', 'Y') // X
    map['Y'] = arrayListOf('X', 'Z') // Y
    map['Z'] = arrayListOf('Y', 'X') // Z

    val signMap = mutableMapOf<Char, Int>()
    signMap['X'] = 1
    signMap['Y'] = 2
    signMap['Z'] = 3

    for (i in input) {
        var temp =
            when (i[0]) {
                'A' -> "X${i[2]}"
                'B' -> "Y${i[2]}"
                else -> "Z${i[2]}"
            }

        var sign = (signMap[temp[1]] ?: 0)
        totalScore += if (temp[0] == temp[1]) {
            // draw
            3 + sign
        } else if (map[temp[1]]?.get(0) != temp[0]) {
            //other
            sign
        } else {
            //win
            6 + sign
        }

    }

    println(totalScore)
    return totalScore
}


//A X
//B Y
//C Z

//score  =  (1 Rock, 2 Paper, 3 Scissors)  + (0  lost, 3 draw, 6 won)
fun partB(): Int {
    var totalScore = 0
//            W   L   D
//    X  ->   Z   Y   X
//    Y  ->   X   Z   Y
//    Z  ->   Y   X   Z

    val map = mutableMapOf<Char, ArrayList<Char>>()
    map['X'] = arrayListOf('Z', 'Y') // X
    map['Y'] = arrayListOf('X', 'Z') // Y
    map['Z'] = arrayListOf('Y', 'X') // Z

    val signMap = mutableMapOf<Char, Int>()
    signMap['X'] = 1
    signMap['Y'] = 2
    signMap['Z'] = 3

    for (i in input) {
        var temp = when (i[0]) {
            'A' -> "X${i[2]}"
            'B' -> "Y${i[2]}"
            else -> "Z${i[2]}"
        }

        when (i[2]) {
            'X' -> {
                // other
                var sign = map[temp[0]]?.get(0)
                totalScore += signMap[sign] ?: 0
//                println("other ${signMap[sign] ?: 0}")
            }
            'Y' -> {
                // draw
                var sign = temp[0]
                totalScore += 3 + (signMap[sign] ?: 0)
//                println("draw ${3 + (signMap[sign] ?: 0)}")
            }
            else -> {
                // win
                var sign = map[temp[0]]?.get(1)
                totalScore += 6 + (signMap[sign] ?: 0)
//                println("win ${6 + (signMap[sign] ?: 0)}")
            }
        }
    }

    println(totalScore)
    return totalScore
}