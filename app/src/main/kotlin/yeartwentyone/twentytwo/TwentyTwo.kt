package yeartwentyone.twentytwo

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/twentytwo/file.txt")) as ArrayList<String>

    Reactor(input)
}

class Reactor(val input: ArrayList<String>) {
    val set = HashSet<String>()

    init {
        partOne()   // 615869
//        partTwo()   // not working yet
    }

    fun partOne() {
        for (i in input) {
            var steps = if (i.contains("on ")) i.replace("on ", "").split(",") else i.replace("off ", "").split(",")
            val x = steps[0].replace("x=", "").split("..")
            val y = steps[1].replace("y=", "").split("..")
            val z = steps[2].replace("z=", "").split("..")

            if (!isInRange(x[0].toInt(), x[1].toInt(), y[0].toInt(), y[1].toInt(), z[0].toInt(), z[1].toInt())) {
                continue
            }
            when {
                i.contains("on ") -> {
                    for (p in Integer.parseInt(x[0])..Integer.parseInt(x[1])) {
                        for (j in Integer.parseInt(y[0])..Integer.parseInt(y[1])) {
                            for (k in Integer.parseInt(z[0])..Integer.parseInt(z[1])) {
                                set.add("$p,$j,$k")
                            }
                        }
                    }
                }
                else -> {
                    for (p in Integer.parseInt(x[0])..Integer.parseInt(x[1])) {
                        for (j in Integer.parseInt(y[0])..Integer.parseInt(y[1])) {
                            for (k in Integer.parseInt(z[0])..Integer.parseInt(z[1])) {
                                set.remove("$p,$j,$k")
                            }
                        }
                    }
                }
            }
        }
        println(set.size)
    }//only -50 to 50

    fun partTwo() {
        set.clear()
        for (i in input) {
            var steps = if (i.contains("on ")) i.replace("on ", "").split(",") else i.replace("off ", "").split(",")
            val x = steps[0].replace("x=", "").split("..")
            val y = steps[1].replace("y=", "").split("..")
            val z = steps[2].replace("z=", "").split("..")

            when {
                i.contains("on ") -> {
                    recursiveSolution(x[0].toInt(), y[0].toInt(), z[0].toInt(), x[1].toInt(), y[1].toInt(), z[1].toInt(), true)
                }
                else -> {
                    recursiveSolution(x[0].toInt(), y[0].toInt(), z[0].toInt(), x[1].toInt(), y[1].toInt(), z[1].toInt(), false)
                }
            }
        }
        println(set.size)
    }

    private tailrec fun recursiveSolution(sx: Int, sy: Int, sz: Int, x: Int, y: Int, z: Int, isOn: Boolean) {
        if (sx == x && sy == y && sz == z) {
            return
        }
        if (isOn) {
            set.add("$sx,$sy,$sz")

        } else {
            set.remove("$sx,$sy,$sz")
        }
        recursiveSolution(sx + 1, sy + 1, sz + 1, x, y, z, isOn)
    }

    private fun isInRange(x1: Int, x2: Int, y1: Int, y2: Int, z1: Int, z2: Int) =
        x1 in -50..50 && x2 in -50..50
                && y1 in -50..50 && y2 in -50..50
                && z1 in -50..50 && z2 in -50..50
}

