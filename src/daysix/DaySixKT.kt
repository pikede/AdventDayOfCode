package daysix

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/daySix/file.txt"))
    println(getNumberOfValidAnswers(input))    // ANS 6583
    println(getNumberOfYesAnswers(input))      // ANS 3290
}


/*
PSEUDO::

set to keep track of each groups unique answers
go through input
if not empty or empty line keep counting unique charachters
else if empty
    add to total
 */
// identify the questions to which anyone answered "yes"
fun getNumberOfValidAnswers(listOfAnswers: List<String>): Int? {
    var cntValidAns = 0
    val set = HashSet<Char>()

    for (element in listOfAnswers) {
        if (element.isNotEmpty()) {
            // same group
            for (letter in element) {
                if (!set.contains(letter)) {
                    cntValidAns++
                    set += letter
                }
            }
        } else {
            // new group
            set.clear()
        }
    }
    return cntValidAns
}
//get each group answer
//process group answer, comparing
//in a char array counting similar characters
// from person to person in a group

// identify the questions to which everyone answered "yes"
fun getNumberOfYesAnswers(listOfAnswers: List<String>): Int {
    var cntValidAns = 0

    var group = Group(ArrayList())

    for (groupAnswer in listOfAnswers) {
        if (groupAnswer.isNotEmpty()) {
            // continue processing same group
            group.ansList.add(groupAnswer)
        } else {
            // new  group, increment valid answer counter
            cntValidAns += if (group.ansList.size == 1) {
                group.ansList[0].length
            } else {
                group.getNumberAnswered()
            }

            // initialise new group
            group = Group(ArrayList())
        }
    }

    // run the last line that was added
    cntValidAns += if (group.ansList.size == 1) {
        group.ansList[0].length
    } else {
        group.getNumberAnswered()
    }

    return cntValidAns
}

data class Group(var ansList: ArrayList<String>)

// Identifies the questions to which everyone answered "yes"!
// in a group having more that one person
private fun Group.getNumberAnswered(): Int {
    val seen = HashSet<Char>()
    ansList[0].forEach { seen.add(it) }

    var cntGroupAnsScore = Int.MAX_VALUE

    for (element in 1 until ansList.size) {
        var cntPlayerScore = 0
        for (answer in ansList[element]) {
            if (seen.contains(answer)) {
                cntPlayerScore++
            }
        }
        cntGroupAnsScore = cntPlayerScore.coerceAtMost(cntGroupAnsScore)
    }

    return cntGroupAnsScore
}