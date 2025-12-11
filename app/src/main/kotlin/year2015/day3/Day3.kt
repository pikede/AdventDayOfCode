package year2015.day3

import AOCPuzzle
import utils.Point2D
import utils.getMoveByArrowDirection
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day3/file.txt"))

fun main() {
    val solution = Day3Solution(questionInput)
    println(solution.part1())
    println(solution.part2())
}

private class Day3Solution(private val puzzleInput: MutableList<String>) : AOCPuzzle {
    override fun part1(): Any {
        var startingPosition = Point2D(0, 0)
        val housesVisited = HashSet<Point2D>()
        housesVisited += startingPosition

        puzzleInput.forEach { locations ->
            locations.forEach {
                val move = it.getMoveByArrowDirection()
                startingPosition = startingPosition.applyMove(move)
                housesVisited += startingPosition
            }
        }

        return housesVisited.size
    }

    override fun part2(): Any {
        var santaPosition = Point2D(0, 0)
        val housesVisited = HashSet<Point2D>()
        housesVisited += santaPosition

        var roboPosition = Point2D(0, 0)
        val roboVisited = HashSet<Point2D>()
        roboVisited += roboPosition

        puzzleInput.forEach { locations ->
            locations.windowed(2,2) {
                val santaNextMove = it[0].getMoveByArrowDirection()
                santaPosition = santaPosition.applyMove(santaNextMove)
                housesVisited += santaPosition

                val roboNextMove = it[1].getMoveByArrowDirection()
                roboPosition = roboPosition.applyMove(roboNextMove)
                roboVisited += roboPosition
            }
        }

        return (housesVisited + roboVisited).size
    }
}