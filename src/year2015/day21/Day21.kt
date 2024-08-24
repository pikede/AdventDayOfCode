package year2015.day21

import AOCPuzzle
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.time.days

private val quizInput: MutableList<String> = Files.readAllLines(Paths.get("src/year2015/day21/file.txt"))
private val boss = Player().apply {
    name = "boss"
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
//    println(day21.part1())
    println(day21.part2())
}

private object Day21Solution : AOCPuzzle {
    @OptIn(ExperimentalStdlibApi::class)
    override fun part1(): Any {
        val items = Items()
        val playerCombinations = mutableListOf<Player>()
        for (weapon in items.weapon) {
            for (armor in items.armor) {
                playerCombinations += (Player(
                    name = "me ${playerCombinations.size + 1}",
                    hitPoints = 100,
                    cost = weapon.cost + armor.cost,
                    damage = weapon.damage,
                    armor = armor.armor,
                ))
            }
        }
        val withoutRings = HashSet(playerCombinations.toHashSet())
        val withRings = HashSet(playerCombinations.toHashSet())
//        for (player in playerCombinations) {
//            addRingsToPlayers(withRings, player)
//        }

        for (temp in ArrayList(playerCombinations)) {
            val player = temp.copy()
            for (ring in ringsList) {
                if (player.canAddRing(ring)) {
                    player.addRing(ring)
                    withRings.add(player.copy())
                }
                for (ring2 in ringsList) {
                    if (ring != ring2 && player.canAddRing(ring2)) {
                        player.addRing(ring2)
                        withRings.add(player.copy())
                    }
                }
            }
        }

        val allPlayer = (withRings + withoutRings).toList()

        var leastGoldSpent = Int.MAX_VALUE
        for (player in allPlayer) {
            if (isPlayerWinner(player, boss.copy())) {
                leastGoldSpent = minOf(leastGoldSpent, player.cost)
            }
        }

        return leastGoldSpent
    }

    override fun part2(): Any {
//        val weapons = mutableListOf<List<Int>>()
//        val armor = mutableListOf<List<Int>>()
//        val rings = mutableListOf<List<Int>>()
//
//        var c = 0
//        for (i in lines) {
//            if (i.isEmpty()) {
//                c += 1
//                continue
//            }
//            var p = i.split(" ").takeLast(3).map { it.toInt() }
//            when (c) {
//                0 -> weapons.add(p)
//                1 -> armor.add(p)
//                2 -> rings.add(p)
//            }
//        }
//
//        armor.add(listOf(0, 0, 0)) // making not wearing armor possible
//        rings.add(listOf(0, 0, 0)) // same for rings
//        rings.add(listOf(0, 0, 0))


        var m = Int.MIN_VALUE

        for (i0 in weaponsList) {
            for (i1 in armorsList) {
                for (combo in ringsList.combinations(2)) {
                    val i2 = combo[0]
                    val i3 = combo[1]

                    val cost = i0.cost + i1.cost + i2.cost + i3.cost
                    val pdmg = i0.damage + i1.damage + i2.damage + i3.damage
                    val parmr = i0.armor + i1.armor + i2.armor + i3.armor
                    val php = 100

                    val player = Player(damage = pdmg, armor = parmr, cost = cost, hitPoints = 100)
                    if (!isPlayerWinner(player, boss.copy())) {
                        m = maxOf(m, cost)
                    }

//                    if (!simulate(php, pdmg, parmr)) {
//                        m = maxOf(m, cost)
//                    }
                }
            }
        }
        return m
    }

    fun <T> List<T>.combinations(k: Int): Sequence<List<T>> = sequence {
        if (k == 0) yield(emptyList())
        else if (this@combinations.isNotEmpty()) {
            val element = this@combinations.first()
            val sublist = this@combinations.drop(1)
            yieldAll(sublist.combinations(k - 1).map { listOf(element) + it })
            yieldAll(sublist.combinations(k))
        }
    }

    fun simulate(php: Int, pdmg: Int, parmr: Int): Boolean {
        var bhp = 104
        var barmr = 1
        var bdmg = 8
        var php = 100
        var b = bhp
        while (true) {
            b -= maxOf(1, pdmg - barmr)
            if (b <= 0) {
                return true
            }
            php -= maxOf(1, bdmg - parmr)
            if (php <= 0) {
                return false
            }
        }
    }

    private fun addRingsToPlayers(all: HashSet<Player>, level: Player) {
        all += level.copy(name = "me ${all.size}")
        if (level !in all) {
        }

        for (ring in ringsList) {
            if (level.canAddRing(ring)) {
                level.addRing(ring)
                addRingsToPlayers(all, level)
                level.removeLastRing(ring)
            }
        }
    }

    private fun isPlayerWinner(player: Player, boss: Player): Boolean {
        while (!hasNoWinner(player, boss)) {
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

    private fun isPlayerLoser(player: Player, boss: Player): Boolean {
        while (!hasNoWinner(player, boss)) {
            player.makeMove(boss)
            if (boss.isLoser()) {
                return false
            }
            boss.makeMove(player)
            if (player.isLoser()) {
                return true
            }
        }
        return false
    }

    private fun hasNoWinner(player: Player, boss: Player) = player.isLoser() || boss.isLoser()

    /*  @OptIn(ExperimentalStdlibApi::class)
      override fun part2(): Any {
          val items = Items()
          val playerCombinations = mutableListOf<Player>()
          for (weapon in items.weapon) {
              for (armor in items.armor) {
                  playerCombinations += (Player(
                      name = "me ${playerCombinations.size + 1}",
                      hitPoints = 100,
                      cost = weapon.cost + armor.cost,
                      damage = weapon.damage,
                      armor = armor.armor,
                  ))
              }
          }
          val withoutRings = HashSet(playerCombinations.toHashSet())
          val withRings = HashSet(playerCombinations.toHashSet())
  //        for (player in playerCombinations) {
  //            addRingsToPlayers(withRings, player)
  //        }

          for (temp in ArrayList(playerCombinations)) {
              val player = temp.copy()
              for (ring in ringsList) {
                  if (player.canAddRing(ring)) {
                      player.addRing(ring)
                      withRings.add(player.copy())
                  }
                  for (ring2 in ringsList) {
                      if (ring != ring2 && player.canAddRing(ring2)) {
                          player.addRing(ring2)
                          withRings.add(player.copy())
                      }
                  }
              }
          }

          val allPlayer = (withRings + withoutRings).toList()

          var leastGodSpent = -9
          for (player in allPlayer) {
              if (!isPlayerWinner(player, boss.copy())) {
                  leastGodSpent = maxOf(leastGodSpent, player.cost)
              }
          }

          return leastGodSpent
      }*/
}

private data class Player(
    var name: String = "",
    var damage: Int = 0,
    var armor: Int = 0,
    var playersRings: MutableList<Rings> = mutableListOf(),
    var hitPoints: Int = 0,
    var cost: Int = 0,
) {
    fun addRing(ring: Rings) {
        damage += ring.damage
        armor += ring.damage
        cost += ring.cost
        playersRings += ring
    }

    fun removeLastRing() {
        val last = playersRings.last()
        playersRings -= last
        damage -= last.damage
        armor -= last.damage
        cost -= last.cost
    }

    fun removeLastRing(ring: Rings) {
        playersRings.remove(ring)// -= ring
        damage -= ring.damage
        armor -= ring.damage
        cost -= ring.cost
    }
}

private fun Player.canAddRing(ring: Rings) = ring !in playersRings && playersRings.size < 2

private fun Player.makeMove(other: Player) {
    val minAllowedHit = maxOf(this.damage - other.armor, 1)
    other.hitPoints -= minAllowedHit
}

private fun Player.isLoser() = hitPoints <= 0

private data class Items(
    val weapon: List<Weapon> = weaponsList,
    val armor: List<Armor> = armorsList,
    val rings: List<Rings> = ringsList
)

private abstract class ShopItems(
    open val name: String,
    open val cost: Int = 0,
    open val damage: Int = 0,
    open val armor: Int = 0
)

private data class Weapon(
    override val name: String,
    override val cost: Int = 0,
    override val damage: Int = 0,
    override val armor: Int = 0
) : ShopItems(name, cost, damage, armor)

private data class Armor(
    override val name: String,
    override val cost: Int = 0,
    override val damage: Int = 0,
    override val armor: Int = 0
) : ShopItems(name, cost, damage, armor)

private data class Rings(
    override val name: String,
    override val cost: Int = 0,
    override val damage: Int = 0,
    override val armor: Int = 0
) : ShopItems(name, cost, damage, armor)

@OptIn(ExperimentalStdlibApi::class)
private val weaponsList = buildList {
    add(Weapon("Dagger", 8, 4, 0))
    add(Weapon("Shortsword", 10, 5, 0))
    add(Weapon("Warhammer", 25, 6, 0))
    add(Weapon("Longsword", 40, 7, 0))
    add(Weapon("Greataxe", 74, 8, 0))
}

@OptIn(ExperimentalStdlibApi::class)
private val armorsList = buildList {
    add(Armor("Leather", 13, 0, 1))
    add(Armor("Chainmail", 31, 0, 2))
    add(Armor("Splintmail", 53, 0, 3))
    add(Armor("Bandedmail", 75, 0, 4))
    add(Armor("Platemail", 102, 0, 5))
    add(Armor("Empty", 0, 0, 0))
}

@OptIn(ExperimentalStdlibApi::class)
private val ringsList = buildList {
    add(Rings("Damage +1", 25, 1, 0))
    add(Rings("Damage +2", 50, 2, 0))
    add(Rings("Damage +3", 100, 3, 0))
    add(Rings("Defense +1", 20, 0, 1))
    add(Rings("Defense +2", 40, 0, 2))
    add(Rings("Defense +3", 80, 0, 3))
    add(Rings("Rings", 0, 0, 0))
    add(Rings("Rings", 0, 0, 0))
}

// tried 73 | 78 correct

// time : 2:25

// 110 too low   || too high 356   ||133