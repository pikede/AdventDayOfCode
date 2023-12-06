package twentythree.daySix

private fun main() {
    val record = RaceRecords()
    println(record.countRecordWithoutKerning())
    println(record.countRecordWithKerning())
}

class RaceRecords {
    // TODO parse inputs and remove hardcoding
    fun countRecordWithoutKerning(): Int {
        val races = ArrayList<RacesRecords>()
        races.add(RacesRecords(44, 208))
        races.add(RacesRecords(80, 1581))
        races.add(RacesRecords(65, 1050))
        races.add(RacesRecords(72, 1102))
        return countRecords(races)
    }

    // TODO parse inputs and remove hardcoding
    fun countRecordWithKerning(): Int {
        val races = ArrayList<RacesRecords>()
        races.add(RacesRecords(44806572, 208158110501102))
        return countRecords(races)
    }

    private fun countRecords(races: ArrayList<RacesRecords>): Int {
        val records = ArrayList<Int>()
        for (race in races) {
            var count = 0
            for (timeHeld in 0..race.raceTime) {
                val distancePerSecond = (race.raceTime - timeHeld)
                val totalDistance = distancePerSecond * timeHeld
                if (totalDistance > race.distanceRecord) {
                    count++
                }
            }
            records.add(count)
        }
        return records.fold(1) { acc, next -> acc * next }
    }
}

data class RacesRecords(val raceTime: Long, val distanceRecord: Long)