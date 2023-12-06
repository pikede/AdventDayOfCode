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
    private val visited = HashMap<String, Long>()
    val startsSeen = HashSet<Long>()

/*    seed-to-soil map:
    soil-to-fertilizer map:
    fertilizer-to-water map:
    water-to-light map:
    light-to-temperature map:
    temperature-to-humidity map:
    humidity-to-location map:
    */

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
        var lowestLocation = Long.MAX_VALUE
        var i = 0
        val visited = HashSet<Long>()
        while (i in seeds.indices) {
            val start = seeds[0]
            val end = start + seeds[1]
            visited.add(start)
            for (seed in start until end) {
                if (seed in visited) {
                    continue
                }
                visited.add(seed)
                val soil = getIngredientTypeB(seed, "seed-to-soil map:")
                val fertilizer = getIngredientTypeB(soil, "soil-to-fertilizer map:")
                val water = getIngredientTypeB(fertilizer, "fertilizer-to-water map:")
                val light = getIngredientTypeB(water, "water-to-light map:")
                val temperature = getIngredientTypeB(light, "light-to-temperature map:")
                val humidity = getIngredientTypeB(temperature, "temperature-to-humidity map:")
                val location = getIngredientTypeB(humidity, "humidity-to-location map:")
                println("seed is at $seed $location")
                lowestLocation = minOf(location, lowestLocation)
            }
            i += 2
        }
        return lowestLocation
    }

    private fun getIngredientTypeB(currentSource: Long, prompt: String): Long {
        val seenPrompt = "$currentSource$prompt"
        if (seenPrompt in visited) {
            return visited[seenPrompt]!!
        }
        val ingredient = ingredients[prompt]!!
        if (currentSource < ingredient[0].source) {
            visited[seenPrompt] = currentSource
            return currentSource
        }
        for (ingredientRange in ingredient) {
            val startSource = ingredientRange.source
            val startDestination = ingredientRange.destination
            val range = ingredientRange.range
            if (currentSource in startDestination until startDestination + range) {
                val typeNeeded = currentSource - startDestination + startSource
                visited[seenPrompt] = typeNeeded
                return typeNeeded
            }
        }
        visited[seenPrompt] = currentSource
        return currentSource
    }

    private fun String.getLongValue() = this.toLong()
}

data class IngredientRange(val source: Long, val destination: Long, var range: Long)