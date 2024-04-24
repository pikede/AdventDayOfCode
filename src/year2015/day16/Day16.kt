package year2015.day16

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val giftsFromOtherAunts: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day16/file.txt"))
private val originalSueGift: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day16/limits.txt"))
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
            val gifts = Gifts(name)
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
                name = gift.getGiftName()
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
                name = gift.getGiftName()
            }
        }
        return name
    }

    private fun givenGift(): Gifts {
        val gift = Gifts("limit")
        originalSueGift.forEach {
            it.split(": ").let { (name, quantity) ->
                gift.addGift(name, quantity.toInt())
                giftType += name
            }
        }
        return gift
    }
}

private fun Gifts.countMatch(other: Gifts): Int {
    var count = 0
    for ((giftName, quantity) in this.getGiftNameAndQuantity()) {
        if (quantity == other.getQuantity(giftName)) {
            count++
        }
    }
    return count
}

private fun Gifts.countMatchUsingRanges(name: String, target: Gifts) = when {
    (name == CATS || name == TREES) && hasGift(name) -> {
        getQuantity(name) > target.getQuantity(name)
    }
    (name == GOLDFISH || name == POMERANIANS) && hasGift(name) -> {
        getQuantity(name) < target.getQuantity(name)
    }
    hasGift(name) -> getQuantity(name) == target.getQuantity(name)
    else -> true
}

class Gifts(private var name: String, private val map: HashMap<String, Int> = hashMapOf()) {

    fun getGiftNameAndQuantity() = map

    fun getQuantity(giftName: String) = map[giftName] ?: 0

    fun hasGift(giftName: String) = giftName in map

    fun getGiftName() = name

    fun addGift(giftType: String, giftAmount: Int) {
        map[giftType] = giftAmount
    }
}
