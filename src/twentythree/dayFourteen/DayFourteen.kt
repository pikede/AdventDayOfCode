package twentythree.dayFourteen

import java.nio.file.Files
import java.nio.file.Paths

private val input: MutableList<String> = Files.readAllLines(Paths.get("src/twentythree/dayFourteen/file.txt"))

fun main() {
    println(input)
}