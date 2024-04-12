package year2015.day15

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths
import java.util.LinkedList
import kotlin.math.max

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day15/file.txt"))

private fun main() {
    val solution = Day15Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day15Solution : AOCPuzzle {
    @OptIn(ExperimentalStdlibApi::class)
    val ingredients = buildList {
        questionInput.forEach {
            val (name, properties) = it.split(": ")
            val (capacity, durability, flavor, texture, calories) = properties.split(", ")
                .map { propertyPerTeaSpoon -> propertyPerTeaSpoon.split(" ") }
                .map { teaSpoon -> teaSpoon[1].toInt() }
            add(Ingredients(name, capacity, durability, flavor, texture, calories))
        }
    }


    override fun part1(): Any {
        var maxScore = 0
        for (i in 0..100) {
            for (j in 0..100 - i) {
                for (k in 0..100 - i - j) {
                    val h = 100 - i - j - k
                    val capacity = maxOf((ingredients[0].capacity * i) + (ingredients[1].capacity * j) + (ingredients[2].capacity * k) + (ingredients[3].capacity * h), 0)
                    val durability = maxOf((ingredients[0].durability * i) + (ingredients[1].durability * j) +(ingredients[2].durability * k) + (ingredients[3].durability * h), 0)
                    val flavor = maxOf((ingredients[0].flavor * i) + (ingredients[1].flavor * j) + (ingredients[2].flavor * k) + (ingredients[3].flavor * h), 0)
                    val texture = maxOf((ingredients[0].texture * i) + (ingredients[1].texture * j) + (ingredients[2].texture * k) + (ingredients[3].texture * h), 0)
                    val score = capacity * durability * flavor * texture
                    maxScore = maxOf(score, maxScore)
                }
            }
        }
        return maxScore
    }

    override fun part2(): Any {
        var maxScore = 0
        for (i in 0..100) {
            for (j in 0..100 - i) {
                for (k in 0..100 - i - j) {
                    val h = 100 - i - j - k
                    val capacity = maxOf((ingredients[0].capacity * i) + (ingredients[1].capacity * j) + (ingredients[2].capacity * k) + (ingredients[3].capacity * h), 0)
                    val durability = maxOf((ingredients[0].durability * i) + (ingredients[1].durability * j) +(ingredients[2].durability * k) + (ingredients[3].durability * h), 0)
                    val flavor = maxOf((ingredients[0].flavor * i) + (ingredients[1].flavor * j) + (ingredients[2].flavor * k) + (ingredients[3].flavor * h), 0)
                    val texture = maxOf((ingredients[0].texture * i) + (ingredients[1].texture * j) + (ingredients[2].texture * k) + (ingredients[3].texture * h), 0)
                    val calories = maxOf((ingredients[0].calories * i) + (ingredients[1].calories * j) + (ingredients[2].calories * k) + (ingredients[3].calories * h), 0)
                    val score = if(calories == 500)capacity * durability * flavor * texture else 0
                    maxScore = maxOf(score, maxScore)
                }
            }
        }
        return maxScore
    }
}

private data class Ingredients(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)