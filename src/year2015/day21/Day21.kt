package year2015.day21

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day21/file.txt"))
private val boss = Player().apply {
    quizInput.map {
        val (attribute, amount) = it.split(": ")
        when (attribute) {
            "Hit Points" -> hitPoints = amount.toInt()
            "Damage" -> damage = amount.toInt()
            "Armor" -> armor = amount.toInt()
        }
    }
}

private fun main() {
    val day21 = Day21Solution
    println(day21.part1())
    println(day21.part2())
}

private object Day21Solution : AOCPuzzle {
    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Any {
        var leastGoldSpent = Int.MAX_VALUE
        for (weapon in weaponsList) {
            for (armor in armorsList) {
                for (ring1 in ringsList) {
                    for (ring2 in ringsList) {
                        if (ring1 == ring2) {
                            continue
                        }
                        val goldSpent = weapon.cost + armor.cost + ring1.cost + ring2.cost
                        val totalDamage = weapon.damage + armor.damage + ring1.damage + ring2.damage
                        val totalArmor = weapon.armor + armor.armor + ring1.armor + ring2.armor
                        val player = Player(damage = totalDamage, armor = totalArmor, hitPoints = 100)
                        if (isPlayerWinner(player, boss.copy())) {
                            leastGoldSpent = minOf(leastGoldSpent, goldSpent)
                        }
                    }
                }
            }
        }
        return leastGoldSpent
    }

    override fun part2(): Any {
        var maxGoldSpent = Int.MIN_VALUE
        for (weapon in weaponsList) {
            for (armor in armorsList) {
                for (ring1 in ringsList) {
                    for (ring2 in ringsList) {
                        if (ring1 == ring2) {
                            continue
                        }
                        val goldSpent = weapon.cost + armor.cost + ring1.cost + ring2.cost
                        val totalDamage = weapon.damage + armor.damage + ring1.damage + ring2.damage
                        val totalArmor = weapon.armor + armor.armor + ring1.armor + ring2.armor
                        val player = Player(damage = totalDamage, armor = totalArmor, hitPoints = 100)
                        if (isPlayerLoser(player, boss.copy())) {
                            maxGoldSpent = maxOf(maxGoldSpent, goldSpent)
                        }
                    }
                }
            }
        }
        return maxGoldSpent
    }

    private fun isPlayerWinner(player: Player, boss: Player): Boolean {
        while (hasNoWinner(player, boss).not()) {
            player.makeMove(boss)
            if (boss.isLoser()) {
                return true
            }
            boss.makeMove(player)
            if (player.isLoser()) {
                return false
            }
        }
        return false
    }

    private fun isPlayerLoser(player: Player, boss: Player) = !isPlayerWinner(player, boss)

    private fun hasNoWinner(player: Player, boss: Player) = player.isLoser() || boss.isLoser()
}

private data class Player(
    var damage: Int = 0,
    var armor: Int = 0,
    var hitPoints: Int = 0,
) {
    fun isLoser() = hitPoints <= 0

    fun makeMove(other: Player) {
        val minAllowedHit = maxOf(this.damage - other.armor, 1)
        other.hitPoints -= minAllowedHit
    }
}

private data class ShopItem(
    val name: String,
    val cost: Int = 0,
    val damage: Int = 0,
    val armor: Int = 0
)

@OptIn(ExperimentalStdlibApi::class)
private val weaponsList = buildList {
    add(ShopItem("Dagger", 8, 4, 0))
    add(ShopItem("Shortsword", 10, 5, 0))
    add(ShopItem("Warhammer", 25, 6, 0))
    add(ShopItem("Longsword", 40, 7, 0))
    add(ShopItem("Greataxe", 74, 8, 0))
}

@OptIn(ExperimentalStdlibApi::class)
private val armorsList = buildList {
    add(ShopItem("Leather", 13, 0, 1))
    add(ShopItem("Chainmail", 31, 0, 2))
    add(ShopItem("Splintmail", 53, 0, 3))
    add(ShopItem("Bandedmail", 75, 0, 4))
    add(ShopItem("Platemail", 102, 0, 5))
    add(ShopItem("Empty", 0, 0, 0))
}

@OptIn(ExperimentalStdlibApi::class)
private val ringsList = buildList {
    add(ShopItem("Damage +1", 25, 1, 0))
    add(ShopItem("Damage +2", 50, 2, 0))
    add(ShopItem("Damage +3", 100, 3, 0))
    add(ShopItem("Defense +1", 20, 0, 1))
    add(ShopItem("Defense +2", 40, 0, 2))
    add(ShopItem("Defense +3", 80, 0, 3))
    add(ShopItem("Rings", 0, 0, 0))
    add(ShopItem("Rings", 0, 0, 0))
}