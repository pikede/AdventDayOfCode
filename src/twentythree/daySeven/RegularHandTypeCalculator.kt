package twentythree.daySeven

class RegularHandTypeCalculator(private val cards: String) : HandTypeCalculator {
    private val cardFrequencies = HashMap<Char, Int>()

    init {
        countCards()
    }

    private fun countCards() {
        cards.forEach {
            cardFrequencies[it] = cardFrequencies.getOrDefault(it, 0) + 1
        }
    }

    override fun getHandType(): HandType {
        val typeCalculator = TypeCalculator(cardFrequencies.values)
        return typeCalculator.calculateHandType()
        /* return when {
             cardCounts.contains(5) -> HandType.FiveOfAKind
             cardCounts.contains(4) -> HandType.FourOfAKind
             cardCounts.contains(3) && cardCounts.contains(2) -> HandType.FullHouse
             cardCounts.contains(3) -> {
                 HandType.ThreeOfAKind
             }
             cardCounts.contains(2) -> {
                 val numberOfPairs = cardCounts.count { it == 2 }
                 if (numberOfPairs == 2) {
                     HandType.TwoPair
                 } else {
                     HandType.OnePair
                 }
             }
             else -> HandType.HighCard
         }*/
    }
}

