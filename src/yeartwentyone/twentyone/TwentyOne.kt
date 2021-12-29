package yeartwentyone.twentyone

//Player 1 starting position: 7
//Player 2 starting position: 10

//Part 1: 802452
//Part 2: not yet done
fun main() {
    var p1 = Player("Player 1", 7)
    var p2 = Player("Player 2", 10)
    var dice = Dice()

    while (true) {
        p1.movePosition(dice.getDiceRoll(), dice.getDiceRoll(), dice.getDiceRoll())
        if (p1.isWinner()) {
            println(p2.getScore() * dice.getTimesRolled())
            break
        }
        p2.movePosition(dice.getDiceRoll(), dice.getDiceRoll(), dice.getDiceRoll())
        if (p2.isWinner()) {
            println(p1.getScore() * dice.getTimesRolled())
            break
        }
    }
}

class Player(private val name: String, startingPosition: Int) {
    private var space = startingPosition
    private var totalScore = 0

    fun movePosition(a: Int, b: Int, c: Int) {
        val newPosition = a + b + c + space
//        space = if (newPosition <= 10)
//            newPosition
//        else
//            getPosition(newPosition)

        space = (newPosition - 1) % 10 + 1
        totalScore += space
    }

    private fun getPosition(position: Int): Int {
        var temp = position
        while (temp > 10) {
            temp -= 10
        }
        return temp
    }

    fun isWinner() = totalScore >= 1000

    fun isWinnerInMultiUniverse() = totalScore >= 21

    fun getScore() = totalScore

    override fun toString() = "$name is at $space with $totalScore"
}

class Dice {
    private var dice = 0
    private var timesRolled = 0

    fun getDiceRoll(): Int {
        timesRolled++
        if (dice + 1 > 100) {
            dice = 1
            return dice
        }
        dice++
        return dice
    }

    fun getTimesRolled() = timesRolled
}


