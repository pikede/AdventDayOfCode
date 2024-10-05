package year2015.day23

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day23/file.txt"))

private fun main() {
    val day22 = Day22Solution
    println(day22.part1())
    println(day22.part2())
}

private object Day22Solution : AOCPuzzle {
    override fun part1() = getFinalValueForRegisterB(aRegisterIntialValue = 0, bRegisterInitialValue = 0)

    override fun part2() = getFinalValueForRegisterB(aRegisterIntialValue = 1L, bRegisterInitialValue = 0L)

    fun getFinalValueForRegisterB(aRegisterIntialValue: Long, bRegisterInitialValue: Long): Long {
        var registerA = aRegisterIntialValue
        var registerB = bRegisterInitialValue
        var index = 0
        while (index in quizInput.indices) {
            val instruction = quizInput[index]
            val split = instruction.replace(",", "").split(" ")

            index += when (split[0]) {
                "hlf" -> {
                    if (split[1] == "a") {
                        registerA /= 2
                    } else {
                        registerB /= 2
                    }
                    1
                }
                "tpl" -> {
                    if (split[1] == "a") {
                        registerA *= 3
                    } else {
                        registerB *= 3
                    }
                    1
                }
                "inc" -> {
                    if (split[1] == "a") registerA++ else registerB++
                    1
                }
                "jmp" -> split[1].toInt()
                "jie" -> {  // if register is even
                    val register = if (split[1] == "a") registerA else registerB
                    if (register % 2 == 0L) {
                        split[2].toInt()
                    } else 1
                }
                "jio" -> {  // if register is 1
                    val register = if (split[1] == "a") registerA else registerB
                    if (register == 1L) {
                        split[2].toInt()
                    } else 1
                }
                else -> throw IllegalArgumentException("Invalid instruction")
            }
        }
        return registerB
    }
}


