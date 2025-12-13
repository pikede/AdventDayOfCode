package yeartwenty.dayone

import org.aoc.utils.readInput

fun main() {
    val listOfReports = readInput("yeartwenty/dayone/file").map { it.toInt() }
    println(get2020ReportRepair(listOfReports, 2020))                // 471019
    println(get2020ReportRepairVariationTwo(listOfReports, 2020))    // 471019
    println(get2020ReportRepairPart2(listOfReports, 2020))           // ANS => 103927824
}

private fun get2020ReportRepair(listOfReports: List<Int>, target: Int): Int {
    for (index in listOfReports.indices) {
        for (j in index + 1 until listOfReports.size) {
            if (listOfReports[index] + listOfReports[j] == target) {
                return listOfReports[index] * listOfReports[j]
            }
        }
    }
    return -1
}

private fun get2020ReportRepairVariationTwo(listOfReports: List<Int>, target: Int): Int {
    for ((index, element) in listOfReports.withIndex()) {
        if (listOfReports.contains(target - listOfReports[index])) {
            return element * find(listOfReports, target - listOfReports[index])
        }
    }
    return -1
}

private fun find(listOfReports: List<Int>, target: Int): Int {
    for (element in listOfReports) {
        if (element == target) {
            return element
        }
    }

    return -1
}

private fun get2020ReportRepairPart2(listOfReports: List<Int>, target: Int): Int {
    for (index in listOfReports.indices) {
        for (innerIndex in index + 1 until listOfReports.size) {
            if (listOfReports.contains(target - (listOfReports[index] + listOfReports[innerIndex]))) {
                return listOfReports[index] * listOfReports[innerIndex] * find(listOfReports, target - (listOfReports[index] + listOfReports[innerIndex]))
            }
        }
    }
    return -1
}