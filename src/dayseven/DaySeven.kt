package dayseven

import java.nio.file.Files
import java.nio.file.Paths

// INCOMPLETE
const val shinyGold = "shiny gold bag"
fun main() {
    val input = Files.readAllLines(Paths.get("src/dayseven/file.txt")) as ArrayList<String>

    println(getNumberOfShinyGoldBags(bagsInput = input))
}


private fun getNumberOfShinyGoldBags(bagsInput: List<String>): Int {
    val map: MutableMap<String, String> = mutableMapOf()
    var cntNumValidBags = 0
    val sortedBagList = mutableListOf<String>()
    sortedBagList.addAll(bagsInput)

    for (bag in bagsInput) {
        val indexOfBags = bag.indexOf(" contain")
        val bagName = bag.substring(0, indexOfBags)

        if (bag.contains(shinyGold) && (bagName != shinyGold && bagName != "${shinyGold}s")) {
            map[bagName] = bag.substring(indexOfBags + 8)

            sortedBagList.remove(bag)
            sortedBagList.add(0, bag)
            cntNumValidBags++
        }
    }

    return cntNumValidBags
}

//if bag contains shiny gold bag and bag name isn't shiny gold bag
    // true -> store bagNames (increment counter)

// else -> does bag contained any bag that have a shiny gold bag
    // if true -> increment counter
    // else move on

