package aoc

import java.io.File

internal fun readInput(fileName: String): List<String>? {
    return object {}::class.java.classLoader.getResource(fileName)
        ?.toURI()
        ?.let { File(it) }
        ?.readLines()
}