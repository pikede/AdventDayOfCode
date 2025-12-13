package org.aoc.utils

import java.io.File

private const val path = "app/src/main/kotlin/"
fun readInput(name: String) = File("$path$name.txt").readLines()
fun readTestInput(name: String) = File("src/inputs", "$name-test.txt").readLines()