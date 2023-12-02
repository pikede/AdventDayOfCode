package twentytwo.six

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/six/file.txt"))

fun main() {
    println(partA())  // 1757
    println(partB())  // 2950
}

fun partA(): Int {
    var phrase = input[0]
    loop@ for (i in phrase.indices) {
        if (i + 4 >= phrase.length) {
            return -2
        }
        var start = i
        var end = i + 4

        var set = HashSet<Char>()
        while (start < end) {
            if (set.contains(phrase[start])) {
                continue@loop
            }
            set.add(phrase[start++])
        }

        return end
    }

    return -1
}

fun partB(): Int {
    var phrase = input[0]
    loop@ for (i in phrase.indices) {
        if (i + 14 >= phrase.length) {
            return -2
        }
        var start = i
        var end = i + 14

        var set = HashSet<Char>()
        while (start < end) {
            if (set.contains(phrase[start])) {
                continue@loop
            }
            set.add(phrase[start++])
        }
        return end
    }

    return -1
}

