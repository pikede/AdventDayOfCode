package yeartwentyone.twelve

import org.aoc.utils.readInput
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

fun main() {
    val input = readInput("yeartwentyone/twelve/file") as ArrayList<String>
    println(CavePaths(input).partOne())  //  3713
    println(CavePaths(input).partTwo())  //  91292
}

class CavePaths(val input: ArrayList<String>) {
    private val caveMap = HashMap<String, HashSet<String>>()

    init {
        for (i in input) {
            val temp = i.split("-")
            if (caveMap[temp[0]] == null) {
                caveMap[temp[0]] = HashSet()
            }
            if (caveMap[temp[1]] == null) {
                caveMap[temp[1]] = HashSet()
            }

            caveMap[temp[0]]?.add(temp[1])
            caveMap[temp[1]]?.add(temp[0])
        }

    }

    fun partOne(): Int {
        var allPaths = ArrayList<Path>()

        allPaths.add(Path(HashSet(), "start"))
        var notFinished = true

        while (notFinished) {
            notFinished = false

            val newPath = ArrayList<Path>()

            for (i in allPaths) {
                if (i.isAtEnd()) {
                    newPath.add(i)
                    continue
                }
                notFinished = true

//                for (lastVisited in caveMap[path.getLastVisited()] ?: emptySet<String>())  or
                for (cave in caveMap[i.getLastVisited()]!!) {
                    if (!i.haveBeenAt(cave)) {
                        newPath.add(Path(i.getVisitedSmallCaves(), cave))
                    }
                }
            }
            allPaths = newPath
        }

        return allPaths.size
    }

    fun partTwo(): Int {
        var allPaths = ArrayList<Path>()

        allPaths.add(Path(HashSet(), "start"))
        var notFinished = true

        while (notFinished) {
            notFinished = false

            val newPath = ArrayList<Path>()

            for (i in allPaths) {
                if (i.isAtEnd()) {
                    newPath.add(i)
                    continue
                }
                notFinished = true

                for (cave in caveMap[i.getLastVisited()]!!) {
                    if (!i.haveBeenAtPartTwo(cave)) {
                        if (i.isMultiCave(cave)) {
                            newPath.add(Path(i, cave, cave))
                        } else {
                            newPath.add(Path(i, cave))

                        }
                    }
                }
            }
            allPaths = newPath
        }

        return allPaths.size
    }

}

class Path {

    constructor(
        path: Path,
        lastVisited: String,
        foundMultiCave: String
    ) : this(path, lastVisited) {
        multiCave = foundMultiCave
    }


    constructor(
        path: Path,
        lastVisited: String
    ) {
        this.visitedSmallCaves.addAll(path.visitedSmallCaves)

        if (lastVisited.lowercase() == lastVisited) {
            this.visitedSmallCaves.add(lastVisited)
        }

        this.lastVisited = lastVisited
        atEnd = "end" == lastVisited
        this.multiCave = path.multiCave
        this.pathString = "${path.pathString} $lastVisited, "
    }

    constructor(
        visitedSmallCaves: HashSet<String>,
        lastVisited: String,
        multiCave: String = ""
    ) {
        this.visitedSmallCaves.addAll(visitedSmallCaves)
        if (lastVisited.lowercase() == lastVisited) {
            this.visitedSmallCaves.add(lastVisited)
        }
        this.lastVisited = lastVisited
        atEnd = "end" == lastVisited
        this.multiCave = multiCave
        this.pathString = "$lastVisited, "
    }

    private var lastVisited = ""
    private var visitedSmallCaves: HashSet<String> = HashSet()
    private var atEnd = false
    private var pathString = ""
    private var multiCave = ""
    private var foundMultiCave = ""

    fun getLastVisited() = lastVisited

    fun isAtEnd() = atEnd

    fun getVisitedSmallCaves() = visitedSmallCaves

    fun haveBeenAt(cave: String): Boolean {
        if (cave.lowercase() == cave) {
            return visitedSmallCaves.contains(cave)
        }
        return false
    }

    fun isMultiCave(cave: String): Boolean {
        if (multiCave.isEmpty() && "end" != cave && cave != "start" && visitedSmallCaves.contains(cave)) {
            return true
        }
        return false
    }

    private fun getPathStr() = pathString

    fun haveBeenAtPartTwo(cave: String): Boolean {
        if (cave.lowercase() == cave) {
            if (multiCave.isEmpty() && "end" != cave && cave != "start" && visitedSmallCaves.contains(cave)) {
                foundMultiCave = cave
                return false
            }
            return visitedSmallCaves.contains(cave)
        }

        return false
    }

    override fun toString(): String {
        return getPathStr()
    }
}