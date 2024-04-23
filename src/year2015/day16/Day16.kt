package year2015.day16

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val questionInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day16/file.txt"))

private fun main() {
    val solution = Day16Solution()
    println(solution.part1())
    println(solution.part2())
}

private class Day16Solution : AOCPuzzle {
    val giftType = HashSet<String>()

    @OptIn(ExperimentalStdlibApi::class)
    val gifts = buildList {
        questionInput.forEach { giftLine ->
            val originalGift = giftLine.replace("Sue ", "")
            val endOfName = originalGift.indexOf(":")
            val name = originalGift.substring(0, endOfName)
            val suesGifts = SuesGifts(name)
            val otherGifts = originalGift.split("$name: ")[1]
            otherGifts.split(", ").map {
                if (it.isEmpty()) {
                    return@map
                }
                val (giftName, quantity) = it.split(": ")
                val giftQuantity = quantity.toInt()
                suesGifts.addGift(giftName, giftQuantity)
            }
            add(suesGifts)
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
                name = gift.name
            }
        }
        return name
    }

    override fun part2(): Any {
        var name = ""
        var maxScore = 0
        for (gift in gifts) {
            var score = 0
            for (name in giftType) {
                score += if (gift.countMatchUsingRanges(name, target)) 1 else 0
            }
            if (maxScore < score) {
                name = gift.name
                maxScore = score
            }
        }
        return name
    }

    private fun givenGift(): SuesGifts {
        val giftsLimits: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day16/limits.txt"))
        val gift = SuesGifts("limit")
        giftsLimits.forEach {
            it.split(": ").let { (name, quantity) ->
                gift.addGift(name, quantity.toInt())
                giftType += name
            }
        }
        return gift
    }
}

private fun SuesGifts.countMatch(other: SuesGifts): Int {
    var count = 0
    for ((gift, quantity) in this.getGiftNameAndQuantity()) {
        if (quantity == other.getQuantity(gift)) {
            count++
        }
    }
    return count
}

private fun SuesGifts.countMatchUsingRanges(name: String, target: SuesGifts) = when {
    (name == "cats" || name =="trees") && hasGift(name) -> {
        getQuantity(name) > target.getQuantity(name)
    }
    (name == "goldfish" || name =="pomeranians") && hasGift(name) -> {
        getQuantity(name) < target.getQuantity(name)
    }
    hasGift(name) -> getQuantity(name) == target.getQuantity(name)
    else -> true
}

data class SuesGifts(var name: String, private val map: HashMap<String, Int> = hashMapOf()) {

    fun getGiftNameAndQuantity() = map

    fun addGift(giftType: String, giftAmount: Int) {
        map[giftType] = giftAmount
    }

    fun getQuantity(giftName: String): Int {
        return map[giftName] ?: 0
    }

    fun hasGift(giftName: String) = giftName in map
}
