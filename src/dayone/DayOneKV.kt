package dayone

import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val listOfReports = mutableListOf<Int>()
    val content = String(Files.readAllBytes(Paths.get("src/dayone/file.txt")))
    val number = StringBuilder()

    for (i in content.indices) {
        if (Character.getNumericValue(content[i]) != -1) {
            number.append(content[i])
        } else {
            listOfReports.add(Integer.valueOf(number.toString()))
            number.clear()
        }
    }

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

// return element if it exist, else return -1 (not found)
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