package twentythree.daySeven

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/daySeven/file.txt"))

private fun main() {
    val camelGame = CamelGame(input)
    println(camelGame.getTotalWinnings())
    println(camelGame.getTotalWinningsWithJoker())
}

class CamelGame(private val camelCards: MutableList<String>) {
    private val gameHands = ArrayList<Hand>()
    private val cardComparator = Comparator<String> { a, b ->
        for (i in a.indices) {
            if (a[i] != b[i]) {
                return@Comparator getCardLevel(a[i]) - getCardLevel(b[i])
            }
        }
        a.compareTo(b)
    }

    init {
        parseGameInput()
    }

    private fun parseGameInput() {
        camelCards.forEach { camelCard ->
            val cardsBet = camelCard.split(" ")
            val cards = cardsBet[0]
            val bet = Integer.parseInt(cardsBet[1])
            val hand = Hand(cards, bet)
            gameHands.add(hand)
        }
    }

    fun getTotalWinnings(): Int {
        val hands = orderHandsByRank(gameHands)
        var totalWinnings = 0
        hands.forEachIndexed { index, hand ->
            totalWinnings += (index + 1) * hand.bid
        }
        return totalWinnings
    }

    private fun orderHandsByRank(hands: ArrayList<Hand>): ArrayList<Hand> {
        val handComparator = Comparator<Hand> { a, b ->
            if (a.getHandType() == b.getHandType()) {
                cardComparator.compare(a.cards, b.cards)
            } else {
                b.getHandType().ordinal - a.getHandType().ordinal
            }
        }
        hands.sortWith(handComparator)
        return hands
    }

    fun getTotalWinningsWithJoker(): Int {
        val hands = orderHandsByRankWithJoker(gameHands)
        var totalWinnings = 0
        hands.forEachIndexed { index, hand ->
            totalWinnings += (index + 1) * hand.bid
        }
        return totalWinnings
    }

    private fun orderHandsByRankWithJoker(hands: ArrayList<Hand>): ArrayList<Hand> {
        val handComparator = Comparator<Hand> { a, b ->
            if (a.getHandTypeWithJoker() == b.getHandTypeWithJoker()) {
                cardComparator.compare(a.cards, b.cards)
            } else {
                b.getHandTypeWithJoker().ordinal - a.getHandTypeWithJoker().ordinal
            }
        }
        hands.sortWith(handComparator)
        return hands
    }

    private fun getCardLevel(card: Char): Int {
        return when (card) {
            'A' -> CardLevel.A.level
            'K' -> CardLevel.K.level
            'Q' -> CardLevel.Q.level
            'J' -> CardLevel.J.level
            'T' -> CardLevel.T.level
            '9' -> CardLevel.Nine.level
            '8' -> CardLevel.Eight.level
            '7' -> CardLevel.Seven.level
            '6' -> CardLevel.Six.level
            '5' -> CardLevel.Five.level
            '4' -> CardLevel.Four.level
            '3' -> CardLevel.Three.level
            else -> CardLevel.Two.level
        }
    }
}

