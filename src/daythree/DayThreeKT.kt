package daythree

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val path = Files.readAllLines(Paths.get("src/daythree/file.txt"))
    println(getNumberOfTreesEncountered(path, moveRightBy = 3, moveDownBy = 1)) // ANS=>  272
    println(getNumberOfTreesEncounteredPart2BigDecimal(treePath = path))        // ANS=> 3898725600
    println(getNumberOfTreesEncounteredPart2Long(treePath = path))              // ANS=> 3898725600
}

// Answer is to big for int primitive, hence BigInteger
fun getNumberOfTreesEncounteredPart2BigDecimal(treePath: List<String>): BigInteger {
    val a = BigInteger(getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 1, moveDownBy = 1).toString())
    val b = BigInteger(getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 3, moveDownBy = 1).toString())
    val c = BigInteger(getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 5, moveDownBy = 1).toString())
    val d = BigInteger(getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 7, moveDownBy = 1).toString())
    val e = BigInteger(getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 1, moveDownBy = 2).toString())

    return a * b * c * d * e
}

// Answer is to big for int primitive, hence long
fun getNumberOfTreesEncounteredPart2Long(treePath: List<String>): Long {
    return (getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 1, moveDownBy = 1).toLong()
            * getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 3, moveDownBy = 1).toLong()
            * getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 5, moveDownBy = 1).toLong()
            * getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 7, moveDownBy = 1).toLong()
            * getNumberOfTreesEncountered(inputMap = treePath, moveRightBy = 1, moveDownBy = 2).toLong())
}

fun getNumberOfTreesEncountered(inputMap: List<String>, moveRightBy: Int, moveDownBy: Int): Int {
    var numberOfTreesEncountered = 0
    var position = 0

    for (i in inputMap.indices step moveDownBy) {
        // check if possible to move down before proceeding
        if (i + moveDownBy < inputMap.size) {
            // get string Below to moveDownBy
            val stringBelow = inputMap[i + moveDownBy]
            position = (position + moveRightBy) % stringBelow.length
            if ('#' == stringBelow[position]) {
                numberOfTreesEncountered++
            }
        }
    }

    return numberOfTreesEncountered
}
