package dayseven

import java.nio.file.Files
import java.nio.file.Paths

const val shinyGold = "shiny gold"
fun main() {
    val input = Files.readAllLines(Paths.get("src/dayseven/file.txt")) as ArrayList<String>

    println(getNumberOfShinyGoldBags(bagsInput = input))
}


private fun getNumberOfShinyGoldBags(bagsInput: List<String>): Int {
    val sortedInputList = mutableListOf<String>()
    val seen: MutableSet<String> = mutableSetOf()
    var cntNumValidBags = 0

    for (bag in bagsInput) {
        val indexOfBags = bag.indexOf(" bags")
        val bagName = bag.substring(0, indexOfBags).toLowerCase()
        if (bag.contains(shinyGold, true)
                && bagName != shinyGold) {
            cntNumValidBags++
            seen.add(bagName)
        } else {
            sortedInputList.add(bag)
        }
    }

    for (bag in sortedInputList) {
        for (item in seen) {
            if (bag.indexOf(item) != -1) {
                cntNumValidBags++
            }
        }

    }

    return cntNumValidBags
}



