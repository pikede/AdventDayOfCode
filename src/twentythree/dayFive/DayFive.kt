package twentythree.dayFive

import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Executors

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFive/file.txt"))

private fun main() {
    val farm = Farm(input)
    println(farm.getLowestSeedLocation())
    println(farm.getLowestSeedLocationWithinRange())  // TODO runs forever run with a dispatcher
}

class Farm(private val inputIngredients: MutableList<String>) {
    private val ingredients = HashMap<String, ArrayList<IngredientRange>>()
    private val seeds = ArrayList<Long>()

    init {
        parseFarmInputs()
    }

    private fun parseFarmInputs() {
        parseSeeds()
        parseSourceToDestination("seed-to-soil map:")
        parseSourceToDestination("soil-to-fertilizer map:")
        parseSourceToDestination("fertilizer-to-water map:")
        parseSourceToDestination("water-to-light map:")
        parseSourceToDestination("light-to-temperature map:")
        parseSourceToDestination("temperature-to-humidity map:")
        parseSourceToDestination("humidity-to-location map:")
    }

    private fun parseSeeds() {
        val seedsPlanted = input[0].split(":")[1]
        seedsPlanted.split(" ").forEach {
            if (it.isNotEmpty()) {
                seeds.add(it.toLong())
            }
        }
    }

    private fun parseSourceToDestination(categoryPrompt: String) {
        val promptIndex = inputIngredients.indexOf(categoryPrompt)
        val startIndex = promptIndex + 1
        val category = ArrayList<IngredientRange>()
        for (i in startIndex..inputIngredients.lastIndex) {
            if (inputIngredients[i].isEmpty() || inputIngredients[i].isBlank()) {
                break
            }
            val ingredient = inputIngredients[i].split(" ")
            val seedSource = ingredient[0].getLongValue()
            val soilDestination = ingredient[1].getLongValue()
            val range = ingredient[2].getLongValue()
            val temp = IngredientRange(seedSource, soilDestination, range)
            category.add(temp)
        }
        category.sortBy { it.source }
        ingredients[categoryPrompt] = category
    }

    fun getLowestSeedLocation(): Long {
        var lowestLocation = Long.MAX_VALUE
        for (seed in seeds) {
            val soil = getIngredientType(seed, "seed-to-soil map:")
            val fertilizer = getIngredientType(soil, "soil-to-fertilizer map:")
            val water = getIngredientType(fertilizer, "fertilizer-to-water map:")
            val light = getIngredientType(water, "water-to-light map:")
            val temperature = getIngredientType(light, "light-to-temperature map:")
            val humidity = getIngredientType(temperature, "temperature-to-humidity map:")
            val location = getIngredientType(humidity, "humidity-to-location map:")
            lowestLocation = minOf(location, lowestLocation)
        }
        return lowestLocation
    }

    private fun getIngredientType(currentSource: Long, prompt: String): Long {
        val ingredient = ingredients[prompt]!!
        if (currentSource < ingredient[0].source) {
            return currentSource
        }
        for (ingredientRange in ingredient) {
            val startSource = ingredientRange.source
            val startDestination = ingredientRange.destination
            val range = ingredientRange.range
            if (currentSource in startDestination until startDestination + range) {
                return currentSource - startDestination + startSource
            }
        }
        return currentSource
    }

    fun getLowestSeedLocationWithinRange(): Long {
        val locations = ArrayList<Long>()

        try {
            val fixedPool = Executors.newFixedThreadPool(12)
            var i = 0
            while (i < seeds.size) {
                val start = seeds[0]
                val end = start + seeds[1]
                fixedPool.submit(TestThread(ingredients, start, end))
                i += 2
            }
            fixedPool.shutdown()
        } catch (e: Exception) {
            println(e.stackTrace)
        }

        return locations.min() ?: -1
    }

    private fun String.getLongValue() = this.toLong()
}

data class IngredientRange(val source: Long, val destination: Long, var range: Long)