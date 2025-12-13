package year2015.day23

import AOCPuzzle
import org.aoc.utils.readInput

private val quizInput= readInput("src/year2015/day23/file")

private fun main() {
    val day23 = Day23Solution
    println(day23.part1())
    println(day23.part2())
}

private object Day23Solution : AOCPuzzle {
    const val A_REGISTER = "a"

    override fun part1() = getFinalValueForRegisterB(aRegisterIntialValue = 0, bRegisterInitialValue = 0)

    override fun part2() = getFinalValueForRegisterB(aRegisterIntialValue = 1L, bRegisterInitialValue = 0L)

    fun getFinalValueForRegisterB(aRegisterIntialValue: Long, bRegisterInitialValue: Long): Long {
        var registerA = aRegisterIntialValue
        var registerB = bRegisterInitialValue
        var index = 0
        while (index in quizInput.indices) {
            val instruction = quizInput[index]
            val split = instruction.replace(",", "").split(" ")
            val currentRegister = split[1]

            index += when (split[0]) {
                "hlf" -> {
                    if (currentRegister == A_REGISTER) {
                        registerA /= 2
                    } else {
                        registerB /= 2
                    }
                    1
                }
                "tpl" -> {
                    if (currentRegister == A_REGISTER) {
                        registerA *= 3
                    } else {
                        registerB *= 3
                    }
                    1
                }
                "inc" -> {
                    if (currentRegister == A_REGISTER) registerA++ else registerB++
                    1
                }
                "jmp" -> currentRegister.toInt()
                "jie" -> {  // if register is even
                    val register = if (currentRegister == A_REGISTER) registerA else registerB
                    if (register % 2 == 0L) split[2].toInt() else 1
                }
                "jio" -> {  // if register is 1
                    val register = if (currentRegister == A_REGISTER) registerA else registerB
                    if (register == 1L) split[2].toInt() else 1
                }
                else -> throw IllegalArgumentException("Invalid instruction")
            }
        }
        return registerB
    }
}


