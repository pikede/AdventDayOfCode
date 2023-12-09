package twentythree.daySeven

data class Hand(val cards: String, val bid: Int) {
    fun getHandType(): HandType {
        val handTypeCalculator = RegularHandTypeCalculator(cards)
        return handTypeCalculator.getHandType()
    }

    fun getHandTypeWithJoker(): HandType {
        val handTypeCalculator = JokerHandTypeCalculator(cards)
        return handTypeCalculator.getHandType()
    }
}
