package twentythree.dayFour

import org.aoc.utils.readInput
import kotlin.math.pow

private val input= readInput("twentythree/dayFour/file").toMutableList()

fun main() {
    val scratchCards = ScratchCards(input)
    println(scratchCards.getScratchPoints())
    println(scratchCards.getOriginalCopiesCount())
}

/*

    Parsing
    Time complexity = n*k*r*m
    Space complexity = n*k*r*m
    n = size of scratchCards
    k = split cards identifier from numbers
    r = split card numbers to winners and played
    m = parse numbers in card numbers hashset

    Part 1: getScratchPoints
    Time complexity : n
    Space complexity: 1

    Part 2: getOriginalCopiesCount
    Time complexity: n * w
    Space = n
    n = size of scratchCards
    w = number of matching winning numbers
* */

class ScratchCards(private val scratchCards: MutableList<String>) {
    private val gameScratchCards = ArrayList<CardSet>()

    init {
        parseAllScratchCards()
    }

    private fun parseAllScratchCards() {
        for (card in scratchCards) {
            val (_, cardNumbers) = card.split(":")
            val (winners, played) = cardNumbers.split("|")
            val winnerCards = parseCardNumbers(winners)
            val playersCards = parseCardNumbers(played)
            val cardSet = CardSet(winnerCards, playersCards)
            gameScratchCards.add(cardSet)
        }
    }

    private fun parseCardNumbers(cardNumbers: String): HashSet<Int> {
        val numbers = cardNumbers.trim().split(" ")
        val cardNumbersIntValues = HashSet<Int>()
        numbers.forEach {
            if (it.isNotEmpty()) {
                val number = Integer.parseInt(it)
                cardNumbersIntValues.add(number)
            }
        }
        return cardNumbersIntValues
    }

    fun getScratchPoints(): Int {
        var totalScore = 0
        for ((winner, player) in gameScratchCards) {
            val commonCards = winner.intersect(player).size
            if (commonCards == 0) {
                continue
            }
            totalScore += commonCards getScore 2.0
        }
        return totalScore
    }

    private infix fun Int.getScore(base: Double) = base.pow(this.toDouble() - 1).toInt()

    fun getOriginalCopiesCount(): Int {
        val list = IntArray(gameScratchCards.size)
        for (index in gameScratchCards.indices) {
            list[index] += 1
            val (winner, player) = gameScratchCards[index]
            val commonCards = winner.intersect(player).size
            for (i in 0 until commonCards) {
                list[index + 1 + i] += list[index]
            }
        }
        return list.sum()
    }
}

data class CardSet(val winningNumbers: HashSet<Int>, val playersNumbers: HashSet<Int>)