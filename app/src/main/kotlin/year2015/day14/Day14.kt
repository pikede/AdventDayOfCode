package year2015.day14

import AOCPuzzle
import org.aoc.utils.readInput

private val questionInput= readInput("src/year2015/day14/file")
private const val RaceTimeLimit = 2503

private fun main() {
    val solution = Day14Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day14Solution : AOCPuzzle {

    override fun part1(): Any {
        val reindeers = getReindeers()
        while (reindeers.any { it.isTimeOver().not() }) {
            for (deer in reindeers) {
                if (deer.isTimeOver()) {
                    continue
                }
                when {
                    deer.shouldRestV1() -> {
                        deer.restV1()
                        deer.resetCurrentTime()
                    }
                    else -> deer.run()
                }
            }
        }

        return reindeers.maxBy { it.getCurrentDistance() }?.getCurrentDistance() ?: 0
    }

    override fun part2(): Any {
        val reindeers = getReindeers()
        while (reindeers.any { it.isTimeOver().not() }) {
            for (deer in reindeers) {
                if (deer.isTimeOver()) {
                    continue
                }
                when {
                    deer.shouldRestV2() -> deer.restV2()
                    else -> deer.run()
                }
            }
            awardCurrentRaceLeaders(reindeers)
        }

        return reindeers.maxBy { it.getTotalPoints() }?.getTotalPoints() ?: 0
    }

    private fun awardCurrentRaceLeaders(reindeers: List<Reindeer>) {
        val maxDistance = reindeers.maxBy { it.getCurrentDistance() }?.getCurrentDistance()
        reindeers.forEach {
            if (it.getCurrentDistance() == maxDistance) {
                it.awardPoint()
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getReindeers() = buildList {
        questionInput.forEach {
            val reindeer = it.split(" ")
            val name = reindeer.first()
            val speed = reindeer[3].toInt()
            val time = reindeer[6].toInt()
            val restTime = reindeer[13].toInt()
            add(Reindeer(name, speed, time, restTime))
        }
    }
}

private data class Reindeer(val name: String, val speed: Int, val time: Int, val restTime: Int) {
    private var totalDistanceRun = 0
    private var currentTime = 0
    private var totalTime = 0
    private var totalPointsAwarded = 0
    private var currentRestTime = 0
    private var totalRestTime = 0

    fun run() {
        totalDistanceRun += speed
        currentTime++
    }

    fun restV1() {
        totalTime += currentTime + restTime
        currentTime = 0
    }

    fun shouldRestV1() = currentTime == time

    fun restV2() {
        totalTime += currentTime
        currentTime = 0
        currentRestTime++
        if (isRestOver()) {
            resetCurrentRestTime()
        }
    }

    fun isRestOver() = currentRestTime == restTime

    fun getCurrentDistance() = totalDistanceRun

    fun shouldRestV2() = currentTime == time || currentRestTime in 1..restTime

    fun resetCurrentTime() {
        currentTime = 0
    }

    fun resetCurrentRestTime() {
        totalRestTime += currentRestTime
        currentRestTime = 0
    }

    fun awardPoint() {
        totalPointsAwarded++
    }

    fun getTotalPoints() = totalPointsAwarded

    fun isTimeOver() = totalRestTime + totalTime >= RaceTimeLimit
}