package twentythree.dayFive

open class TestThread(
    private val ingredients: HashMap<String, ArrayList<IngredientRange>>,
    var start: Long,
    var end: Long
) : Runnable {
    private val seen = HashSet<Long>()

    override fun run() {
        getLowestSeedLocationWithinRange()
    }

    private fun getLowestSeedLocationWithinRange(): Long {
        var lowestLocation = Long.MAX_VALUE
        for (seed in start until end) {
            if (seed in seen) {
                continue
            }
            seen.add(seed)
            val soil = getIngredientTypeB(seed, "seed-to-soil map:")
            val fertilizer = getIngredientTypeB(soil, "soil-to-fertilizer map:")
            val water = getIngredientTypeB(fertilizer, "fertilizer-to-water map:")
            val light = getIngredientTypeB(water, "water-to-light map:")
            val temperature = getIngredientTypeB(light, "light-to-temperature map:")
            val humidity = getIngredientTypeB(temperature, "temperature-to-humidity map:")
            val location = getIngredientTypeB(humidity, "humidity-to-location map:")
            println(location)
            lowestLocation = minOf(location, lowestLocation)
        }
        println("lowest location $lowestLocation")
        return lowestLocation
    }

    private fun getIngredientTypeB(currentSource: Long, prompt: String): Long {
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
}
// 881943038, 3038