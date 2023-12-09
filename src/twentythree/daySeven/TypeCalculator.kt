package twentythree.daySeven

class TypeCalculator(private val cardFrequencies: Collection<Int>) {

    fun calculateHandType(): HandType {
        return when {
            cardFrequencies.contains(5) -> HandType.FiveOfAKind
            cardFrequencies.contains(4) -> HandType.FourOfAKind
            cardFrequencies.contains(3) && cardFrequencies.contains(2) -> HandType.FullHouse
            cardFrequencies.contains(3) -> HandType.ThreeOfAKind
            cardFrequencies.contains(2) -> {
                val numberOfPairs = cardFrequencies.count { it == 2 }
                if (numberOfPairs == 2) {
                    HandType.TwoPair
                } else {
                    HandType.OnePair
                }
            }
            else -> HandType.HighCard
        }
    }
}