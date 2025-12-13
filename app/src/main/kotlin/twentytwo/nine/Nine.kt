package twentytwo.nine

import java.lang.Exception
import org.aoc.utils.readInput

val rawInput = readInput("twentytwo/nine/file")

fun main() {
    var input = mutableListOf<Instruction>()

    for (i in rawInput) {
        var split = i.split(" ")
        print(split)
        input.add(Instruction(split[0], split[1].toInt()))
    }


}

/*fun main() {
        fun myAtoi(s: String): Int {
            if(s.isEmpty()) return 0

            var input = s.trim()
            var hasStarted = false


            var startIndex = 0

            while(startIndex < s.length && s[startIndex] ==' '){
                startIndex++
            }
            if(startIndex !in s.indices){
                return 0
            }

            if(s[startIndex].isLetter()){
                return 0
            }

            while(!hasStarted && startIndex in s.indices){
                if(s[startIndex].getIntValue() in 0..9
                    || s[startIndex] == '-'){

                    hasStarted = true
                    break
                }
                startIndex++
            }

            val sign = if(s[startIndex] =='-'){
                startIndex++
                -1
            }  else 1

            var finalAns = 0

            //    while(i<n){
            //         int num = str.charAt(i)-'0';
            //         if(num>=0 && num<=9){
            //         if (result > (Integer.MAX_VALUE - num) / 10) return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE; //bounds

            //             result = result *10 + num;

            //         } else{
            //             break;
            //         }
            //         i++;
            //     }

            while(startIndex in s.indices){
                // && s[startIndex].getIntValue() in 0..9
                // ){
                val num = s[startIndex++].getIntValue()

                if (finalAns > (Int.MAX_VALUE - num) / 10){
                        return if(sign == 1 ) Int.MAX_VALUE else Int.MIN_VALUE
                    } else {
                    finalAns    = num + (finalAns * 10)
                }
            }

            return sign * finalAns
        }

}*/


fun Char.getIntValue(): Int {
    return this - '0'
}

class Board(val input: MutableList<Instruction>) {

    private val matrix = arrayOf(
        charArrayOf('.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.'),
        charArrayOf('.', '.', '.', '.', '.', '.')
    )
    var head = matrix.size to 0
    var tail = matrix.size to 0
    var starting = matrix.size to 0
    val visitedTailPositions = mutableSetOf<Pair<Int, Int>>()

    init {
        partA()
    }

    private fun partA(): Int {
        visitedTailPositions.add(tail)

        for (i in input) {
            when (i.directions) {
                "U" -> moveUpBy(i.moveBy)
                "D" -> moveDownBy(i.moveBy)
                "L" -> moveLeftBy(i.moveBy)
                "R" -> moveRightBy(i.moveBy)
                else -> throw Exception("Missing movement type")
            }
            visitedTailPositions.add(tail)
        }

        return visitedTailPositions.size
    }

    // remove from row for H,  keep col  (-ve row)
    fun moveUpBy(increment: Int) {
        if (head.first - increment in matrix.indices) {

        }
    }

    // add to row for H,  keep col  (+ve row)
    fun moveDownBy(increment: Int) {

    }

    // remove from col for H,  keep row  (-ve col)
    fun moveLeftBy(increment: Int) {

    }

    // add to col for H,  keep row  (+ve col)
    fun moveRightBy(increment: Int) {

    }

    fun adjustTail() {
        
    }
}

data class Instruction(val directions: String, val moveBy: Int)