package twentythree.daySeven

class JokerHandTypeCalculator(private val cards: String) : HandTypeCalculator {
    private val cardFrequencies = HashMap<Char, Int>()

    init {
        countCards()
    }

    private fun countCards() {
        cards.forEach {
            if (it != 'J') {
                cardFrequencies[it] = cardFrequencies.getOrDefault(it, 0) + 1
            }
        }
    }

    override fun getHandType(): HandType {
        val typeCalculator = TypeCalculator(cardFrequencies.values)
        val jokerCount = cards.count { it == 'J' }
        if (jokerCount == 0) {
            return typeCalculator.calculateHandType()
        }
        val maxCard = getMaxCard()
        cardFrequencies[maxCard] = (cardFrequencies[maxCard] ?: 0) + jokerCount
        return typeCalculator.calculateHandType()
    }

    private fun getMaxCard(): Char {
        var maxCount = 0
        var maxCard = cards[0]
        for (card in cards) {
            val count = cardFrequencies[card] ?: 0
            if (count >= maxCount) {
                maxCount = count
                maxCard = card
            }
        }
        return maxCard
    }
}