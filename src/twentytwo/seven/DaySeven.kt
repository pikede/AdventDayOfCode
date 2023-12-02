package twentytwo.seven

import java.nio.file.Files
import java.nio.file.Paths

val input = Files.readAllLines(Paths.get("src/twentytwo/seven/file.txt"))
// TODO comeback

fun main() {
    partA()

}

fun partA() {
    val map = mutableMapOf<Char, Any>()

    for (i in input) {
        if (i.contains("/")) {
            continue
        }
    }
}

class TerminalPuzzle(inputGroup: List<String>) {

    fun moveUpALevel() {}

    fun addDir(){}

    fun moveInto(){}
}