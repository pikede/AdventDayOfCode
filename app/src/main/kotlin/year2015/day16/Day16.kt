package year2015.day16

import AOCPuzzle
import org.aoc.utils.readInput

private val giftsFromOtherAunts= readInput("src/year2015/day16/file")
private val originalSueGift= readInput("src/year2015/day16/limits.")
private const val CATS = "cats"
private const val TREES = "trees"
private const val GOLDFISH = "goldfish"
private const val POMERANIANS = "pomeranians"

private fun main() {
    val solution = Day16Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day16Solution : AOCPuzzle {
    val giftType = HashSet<String>()

    @OptIn(ExperimentalStdlibApi::class)
    val gifts = buildList {
        giftsFromOtherAunts.forEach { giftLine ->
            val originalGift = giftLine.replace("Sue ", "")
            val endOfName = originalGift.indexOf(":")
            val name = originalGift.substring(0, endOfName)
            val gifts = AuntSueGift(name)
            val otherGifts = originalGift.split("$name: ")[1]
            otherGifts.split(", ").map {
                if (it.isEmpty()) {
                    return@map
                }
                val (giftName, quantity) = it.split(": ")
                val giftQuantity = quantity.toInt()
                gifts.addGift(giftName, giftQuantity)
            }
            add(gifts)
        }
    }
    val target = givenGift()

    override fun part1(): Any {
        var maxScore = 0
        var name = ""
        val target = givenGift()
        for (gift in gifts) {
            if (target.countMatch(gift) > maxScore) {
                maxScore = target.countMatch(gift)
                name = gift.getAuntsName()
            }
        }
        return name
    }

    override fun part2(): Any {
        var name = ""
        var maxScore = 0
        for (gift in gifts) {
            var score = 0
            for (giftName in giftType) {
                score += if (gift.countMatchUsingRanges(giftName, target)) 1 else 0
            }
            if (maxScore < score) {
                maxScore = score
                name = gift.getAuntsName()
            }
        }
        return name
    }

    private fun givenGift(): AuntSueGift {
        val gift = AuntSueGift("limit")
        originalSueGift.forEach {
            it.split(": ").let { (name, quantity) ->
                gift.addGift(name, quantity.toInt())
                giftType += name
            }
        }
        return gift
    }
}

private fun AuntSueGift.countMatch(other: AuntSueGift): Int {
    var count = 0
    for ((giftName, quantity) in this.getGiftNameAndQuantity()) {
        if (quantity == other.getQuantity(giftName)) {
            count++
        }
    }
    return count
}

private fun AuntSueGift.countMatchUsingRanges(name: String, target: AuntSueGift) = when {
    (name == CATS || name == TREES) && hasGift(name) -> {
        getQuantity(name) > target.getQuantity(name)
    }
    (name == GOLDFISH || name == POMERANIANS) && hasGift(name) -> {
        getQuantity(name) < target.getQuantity(name)
    }
    hasGift(name) -> getQuantity(name) == target.getQuantity(name)
    else -> true
}

class AuntSueGift(private var auntsName: String, private val map: HashMap<String, Int> = hashMapOf()) {

    fun getGiftNameAndQuantity() = map

    fun getQuantity(giftName: String) = map[giftName] ?: 0

    fun hasGift(giftName: String) = giftName in map

    fun getAuntsName() = auntsName

    fun addGift(giftType: String, giftAmount: Int) {
        map[giftType] = giftAmount
    }
}
