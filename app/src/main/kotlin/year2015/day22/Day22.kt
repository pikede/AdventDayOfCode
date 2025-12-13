package year2015.day22

import java.lang.Integer.MAX_VALUE

// TODO make into usable objects, create parent data class with each as a data class internally
// 0=manacost, 1=dmg, 2=hp, 3=armor, 4=mana, 5=turns, 6=index
val missile = listOf(53, 4, 0, 0, 0, 0, 0)
val drain = listOf(73, 2, 2, 0, 0, 0, 1)
val shield = listOf(113, 0, 0, 7, 0, 6, 2)
val poison = listOf(173, 3, 0, 0, 0, 6, 3)
val recharge = listOf(229, 0, 0, 0, 101, 5, 4)
val spells = listOf(missile, drain, shield, poison, recharge)
var leastManaUsed = MAX_VALUE
var partTwo = true

fun main() {
    simulate(58, 50, 500, emptyList(), true, 0)
    println("Least mana used: $leastManaUsed")
}

fun simulate(bossHP: Int, myHP: Int, myMana: Int, activeSpells: List<List<Int>>, playerTurn: Boolean, manaUsed: Int): Boolean {
    var bossHpVar = bossHP
    var myHpVar = myHP
    var myManaVar = myMana
    val bossDmg = 9
    var myArmor = 0

    // Part two rule, lose 1 HP on player's turn
    if (partTwo && playerTurn) {
        myHpVar -= 1
        if (myHpVar <= 0) return false
    }

    // Apply effects of active spells
    val newActiveSpells = mutableListOf<List<Int>>()
    for (activeSpell in activeSpells) {
        if (activeSpell[5] >= 0) { // Spell effect applies now
            bossHpVar -= activeSpell[1]
            myHpVar += activeSpell[2]
            myArmor += activeSpell[3]
            myManaVar += activeSpell[4]
        }

        val newActiveSpell = activeSpell.toMutableList()
        newActiveSpell[5] = activeSpell[5] - 1
        if (newActiveSpell[5] > 0) { // Spell continues
            newActiveSpells.add(newActiveSpell)
        }
    }

    // If boss is dead, check mana used
    if (bossHpVar <= 0) {
        if (manaUsed < leastManaUsed) {
            leastManaUsed = manaUsed
        }
        return true
    }

    // If too much mana used, exit
    if (manaUsed >= leastManaUsed) return false

    if (playerTurn) {
        // Player's turn: try casting spells
        for (spell in spells) {
            val spellAlreadyActive = newActiveSpells.any { it[6] == spell[6] }
            val spellManaCost = spell[0]

            if (spellManaCost <= myManaVar && !spellAlreadyActive) {
                val updatedSpells = newActiveSpells.toMutableList()
                updatedSpells.add(spell)
                simulate(bossHpVar, myHpVar, myManaVar - spellManaCost, updatedSpells, false, manaUsed + spellManaCost)
            }
        }
    } else {
        // Boss's turn: boss attacks
        myHpVar += if (myArmor - bossDmg < 0) myArmor - bossDmg else -1
        if (myHpVar > 0) {
            simulate(bossHpVar, myHpVar, myManaVar, newActiveSpells, true, manaUsed)
        }
    }
    return false
}

