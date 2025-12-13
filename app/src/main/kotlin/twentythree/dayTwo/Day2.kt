package twentythree.dayTwo

import org.aoc.utils.readInput

private val input= readInput("twentythree/dayTwo/file").toMutableList()

fun main() {
    val game = BallGame(input)
    println(game.getPlayableGames())
    println(game.getGamesSumOfPowerSet())
}

class BallGame(val input: MutableList<String>) {
    private val games = ArrayList<GameBallCounts>()

    init {
        for (game in input) {
            val gameDetail = game.split(": ")
            val combination = getBallCombination(gameDetail[1])
            games.add(combination)
        }
    }

    private fun getBallCombination(gameCombination: String): GameBallCounts {
        val subGame = gameCombination.split("; ")
        val combination = GameBallCounts(0, 0, 0)
        for (bag in subGame) {
            val splitBag = bag.split(", ")
            for (countAndColour in splitBag) {
                val ballCount = countAndColour.split(" ")
                when {
                    ballCount[1].contains(BallColors.RED.name, true) -> {
                        val max = maxOf(Integer.parseInt(ballCount[0]), combination.red)
                        combination.red = max
                    }
                    ballCount[1].contains(BallColors.GREEN.name, true) -> {
                        val max = maxOf(Integer.parseInt(ballCount[0]), combination.green)
                        combination.green = max
                    }
                    ballCount[1].contains(BallColors.BLUE.name, true) -> {
                        val max = maxOf(Integer.parseInt(ballCount[0]), combination.blue)
                        combination.blue = max
                    }
                }
            }
        }
        return combination
    }

    fun getPlayableGames(): Int {
        var count = 0
        for (gameId in games.indices) {
            // limits 12 red cubes, 13 green cubes, and 14 blue cubes
            if (isGamePlayable(games[gameId])) {
                count += (gameId + 1)
            }
        }
        return count
    }

    private fun isGamePlayable(game: GameBallCounts) = game.blue <= 14 && game.red <= 12 && game.green <= 13

    fun getGamesSumOfPowerSet(): Int {
        var powerSetSum = 0
        for (game in games) {
            powerSetSum += game.green * game.red * game.blue
        }
        return powerSetSum
    }

}

data class GameBallCounts(var red: Int, var green: Int, var blue: Int)

enum class BallColors{
    GREEN,
    RED,
    BLUE
}

