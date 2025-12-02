package twenty5.day2

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/twenty5/day2/file.txt"))

private fun main() {
    println(Day2.part1()) //31839939622
    println(Day2.part2())
}

private object Day2 : AOCPuzzle {

    override fun part1(): Long {
        var sum = 0L
        val ids = quizInput[0].split(",")
        ids.forEach { currentId ->
            val (start, end) = currentId.split("-").map { it.toLong() }
            for (i in start..end) {
                val stringValue = i.toString()
                val half = stringValue.length
                if (half % 2 != 0) continue
                val first = stringValue.substring(0, half/2)
                val second = stringValue.substring(half/2)
                if (first == second) {
                    sum += i
                }
            }
        }
        return sum
    }

    override fun part2(): Int {
        return 0
    }

}


// 11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124