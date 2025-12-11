package twentythree.dayFive

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFive/file.txt"))

private fun main() {
    val farm = Farm(input)
    println(farm.getLowestSeedLocation())
    println(farm.getLowestSeedLocationWithinRange())
}

class Farm(private val inputIngredients: MutableList<String>) {
    private val ingredients = HashMap<String, ArrayList<IngredientRange>>()
    private val seeds = ArrayList<Long>()
    private val ranges = HashMap<String, ArrayList<Pair<Long, Long>>>()

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

    private fun String.getLongValue() = this.toLong()

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
        var i = 0
        val locations = ArrayList<Long>()
        while (i in seeds.indices) {
            val start = seeds[i]
            val end = seeds[i] + seeds[i + 1] - 1
            val temp = ranges["seed"] ?: ArrayList()
            temp.add(start to end)
            val (soilA, soilB) = getStartToEnd(start, end, "seed-to-soil map:")
            val (fertilizerA, fertilizerB) = getStartToEnd(soilA, soilB, "soil-to-fertilizer map:")
            val (waterA, waterB) = getStartToEnd(fertilizerA, fertilizerB, "fertilizer-to-water map:")
            val (lightA, lightB) = getStartToEnd(waterA, waterB, "water-to-light map:")
            val (temperatureA, temperatureB) = getStartToEnd(lightA, lightB, "light-to-temperature map:")
            val (humidityA, humidityB) = getStartToEnd(temperatureA, temperatureB, "temperature-to-humidity map:")
            val (locationA, locationB) = getStartToEnd(humidityA, humidityB, "temperature-to-humidity map:")
            locations.add(locationA)
            locations.add(locationB)
            i += 2
        }
        return locations.min() ?: 0L
    }

    private fun getStartToEnd(startSource: Long, endSource: Long, prompt: String): Pair<Long, Long> {
        val start = getIngredientType(startSource, prompt)
        val end = getIngredientType(endSource, prompt)
        val temp = ranges[prompt] ?: ArrayList()
        temp.add(start to end)
        ranges[prompt] = temp
        return start to end
    }
}

data class IngredientRange(val source: Long, val destination: Long, var range: Long)