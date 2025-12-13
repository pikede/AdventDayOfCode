package yeartwenty.dayfour

import org.aoc.utils.readInput

fun main() {
    val passportLists = readInput("yeartwenty/dayfour/file")
    println(getNumValidPassportsPart1(passportLists))   // ANS: 202
    println(getNumValidPassportsPart2(passportLists))   // ANS: 137
}

fun getNumValidPassportsPart1(passportList: List<String>): Int {
    var passport = Passport()
    var cntValidPassports = 0

    for (inputPassport in passportList) {
        if (inputPassport.isNotEmpty()) {
            // same passport object
            passport.addPassport(inputPassport).also { passport = it }
        } else {
            // new passport object
            with(passport) {
                if(byr != 0 && iyr != null && eyr != null && hgt != null && hcl != null && ecl != null && pid != null){
                    cntValidPassports++
                }
            }
            passport = Passport()
        }
    }

    return cntValidPassports
}

//byr (Birth Year) - four digits; at least 1920 and at most 2002.
//iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//hgt (Height) - a number followed by either cm or in:
//If cm, the number must be at least 150 and at most 193.
//If in, the number must be at least 59 and at most 76.
//hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//pid (Passport ID) - a nine-digit number, including leading zeroes.
//cid (Country ID) - ignored, missing or not.
private fun getNumValidPassportsPart2(passportList: List<String>): Int {
    var numValid = 0
    var passport = Passport()
    for (inputPassport in passportList) {
        if (inputPassport.isNotEmpty()) {
            // same passport object
            passport.addPassport(inputPassport).also { passport = it }
        } else {
            // new passport object
            if (passport.isValidPassport()) {
                numValid++
            }

            passport = Passport()
        }
    }

    return numValid
}

data class Passport(
        var byr: Int = 0,
        var iyr: Int? = null,
        var eyr: Int? = null,
        var hgt: String? = null,
        var hcl: String? = null,
        var ecl: String? = null,
        var pid: String? = null,
        var cid: String? = null
)

@JvmName(name = "addPassport")
fun Passport.addPassport(input: String): Passport {
    var arr = input.split(" ")

    for (element in arr) {
        val beforeColon = element.substring(0..2)
        val afterColon = element.substring(4)
        when (beforeColon) {
            "byr" -> byr = afterColon.toInt()
            "iyr" -> iyr = afterColon.toInt()
            "eyr" -> eyr = afterColon.toInt()
            "hgt" -> hgt = afterColon
            "hcl" -> hcl = afterColon
            "ecl" -> ecl = afterColon
            "pid" -> pid = afterColon
            "cid" -> cid = afterColon
        }

    }
    return this
}

fun Passport.isValidPassport(): Boolean {
    return isBYRValid()
            && isEYRValid()
            && isECLValid()
            && isHCLValid()
            && isHeightValid()
            && isIYRValid()
            && isPIDValid()
}

fun Passport.isBYRValid(): Boolean {
    return byr in 1920..2002
}

fun Passport.isIYRValid() = iyr in 2010..2020

fun Passport.isEYRValid() = eyr in 2020..2030

fun Passport.isHeightValid(): Boolean {
    hgt?.let {
        if (it.contains("cm")) {
            return it.substring(0, it.length - 2).toInt() in 150..193
        } else if (it.contains("in")) {
            return it.substring(0, it.length - 2).toInt() in 59..76
        }
    }
    return false
}

fun Passport.isHCLValid(): Boolean {
    hcl?.let {
        return it[0] == '#'
                && it.length == 7
    }

    return false
}

fun Passport.isECLValid(): Boolean {
    ecl?.let {
        val set = mutableSetOf<String>()
        set += "amb"
        set += "blu"
        set += "brn"
        set += "gry"
        set += "grn"
        set += "hzl"
        set += "oth"
        return it in set
    }

    return false
}

fun Passport.isPIDValid() = pid?.length == 9

