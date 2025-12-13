package yeartwenty.daytwo

import org.aoc.utils.readInput

const val dash = '-'
const val colon = ':'
const val space = ' '

fun main() {
    val listOfPasswords = readInput("yeartwenty/daytwo/file")

    println(getNumberOfValidPasswordsPartOne(listOfPasswords))   // ANS=> 622
    println(getNumberOfValidPasswordsPartTwo(listOfPasswords))   // ANS => 263
}

private fun getNumberOfValidPasswordsPartOne(listOfPasswords: List<String>): Int {
    var cntValidPassword = 0

    for (passwordPolicy in listOfPasswords) {
        // sample passwordPolicy -> 4-8 n: dnjjrtclnzdnghnbnn
        val dashIndex = passwordPolicy.indexOf(dash)       // index of dash position in password
        val colonIndex = passwordPolicy.indexOf(colon)     // index of first space position in password
        val spaceIndex = passwordPolicy.indexOf(space)     // index of colon position in password
        val low = Integer.parseInt(passwordPolicy.substring(0, dashIndex))    // lower frequency
        val high = Integer.parseInt(passwordPolicy.substring(dashIndex + 1, spaceIndex))    // higher frequency
        val policyChar = passwordPolicy.substring(spaceIndex + 1, colonIndex)      // password character to examine
        val password = passwordPolicy.substring(colonIndex + 1)          // entered password

        var frequency = 0
        // count number of character occurrences
        for (characterCnt in password.indices) {
            if (policyChar[0] == password[characterCnt]) {
                frequency++
            }
        }

        // character count must fall within the required limit/range
        if (frequency in low..high) {
            cntValidPassword++
        }
    }
    return cntValidPassword
}


private fun getNumberOfValidPasswordsPartTwo(listOfPasswords: List<String>): Int {
    var cntValidPassword = 0

    for (passwordPolicy in listOfPasswords) {
        val dashIndex = passwordPolicy.indexOf(dash)       // index of dash position in password
        val colonIndex = passwordPolicy.indexOf(colon)     // index of first space position in password
        val spaceIndex = passwordPolicy.indexOf(space)     // index of colon position in password
        val first = Integer.parseInt(passwordPolicy.substring(0, dashIndex))    // first index occurrence
        val second = Integer.parseInt(passwordPolicy.substring(dashIndex + 1, spaceIndex))    // second index occurrence
        val policyChar = passwordPolicy.substring(spaceIndex + 1, colonIndex)[0]      // password character to examine
        val password = passwordPolicy.substring(colonIndex + 1)          // entered password

        // policyChar must occur at one of these positions/indexes to be valid
        if ((password[first] == policyChar && password[second] != policyChar) ||
                password[second] == policyChar && password[first] != policyChar) {
            cntValidPassword++
        }
    }
    return cntValidPassword
}



