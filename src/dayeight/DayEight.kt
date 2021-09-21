package dayeight

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/dayeight/input"))
    println(Accumulator(inputList = input).accumulateList())
    println(Accumulator(inputList = input).accumulateList2())
}

class Accumulator(private val inputList: List<String>) {
    private var currentAccumulatedValue: Int = 0
    private var index: Int = 0
    private val commandLocation: MutableSet<Int> = mutableSetOf()

    fun accumulateList(): Int {
        while (!false) {
            val commandLine = inputList[index]
            val commandType = getCommandType(commandLine)
            val commandValue = getCommandValue(commandLine)

            var temp = when (commandType) {
                "acc" -> moveAccumulator(commandValue)
                "jmp" -> commandValue
                else -> 1
            }

            index += temp
            if (index > inputList.size) {
                index %= inputList.size
            } else if (index < 0) {
                index += inputList.size
            }

            if (commandLocation.contains(index)) {
                return currentAccumulatedValue
            } else {
                commandLocation.add(index)
            }
        }

        return currentAccumulatedValue
    }

    fun accumulateList2(): Int {
        while (!false) {
            val commandLine = inputList[index]
            val commandType = getCommandType(commandLine)
            val commandValue = getCommandValue(commandLine)

            var temp = when (commandType) {
                "acc" -> moveAccumulator(commandValue)
                "jmp" -> commandValue
                else -> 1
            }

            if (temp + index == inputList.size - 1 ) {
                println("$commandLine \n $commandValue at $index")
                return currentAccumulatedValue
            }

            index += temp
            if (index > inputList.size) {
                index %= inputList.size
            } else if (index < 0) {
                index += inputList.size
            }

            if (commandLocation.contains(index)) {
                return currentAccumulatedValue
            } else {
                commandLocation.add(index)
            }
        }

        return currentAccumulatedValue
    }

    private fun getCommandType(command: String): String {
        return command.substring(0, 3)
    }

    private fun getCommandValue(command: String): Int {
        return command.substring(4).toInt()
    }

    private fun moveAccumulator(incrementBy: Int): Int {
        currentAccumulatedValue += incrementBy
        return 1
    }

}