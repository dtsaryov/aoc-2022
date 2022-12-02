package aoc

import java.io.File

/**
 * [AoC 2022: Day 1](https://adventofcode.com/2022/day/1)
 */
@Suppress("unused")
fun findMaxCalories(): Int {
    val input = readInput() ?: return -1

    var max = 0
    processSums(input) { sum ->
        if (sum > max) max = sum
    }
    return max
}

@Suppress("unused")
fun findSumOfTop3Calories(): Int {
    val input = readInput() ?: return -1

    val top3 = Top3()
    processSums(input, top3::add)

    return top3.getValues().fold(0, Int::plus)
}

private class Top3 {
    private val values = arrayOf(-1, -1, -1)

    fun add(n: Int) {
        var minIdx = 0

        for (i in 1..2)
            if (values[i] < values[minIdx]) minIdx = i

        if (values[minIdx] < n)
            values[minIdx] = n
    }

    fun getValues() = values
}

private fun readInput(): List<String>? {
    return object {}::class.java.classLoader.getResource("day1.txt")
        ?.toURI()
        ?.let { File(it) }
        ?.readLines()
}

private fun processSums(input: List<String>, processor: (Int) -> Unit) {
    var current = 0
    for (line in input) {
        if (line.isEmpty()) {
            processor(current)
            current = 0
        } else {
            current += line.toInt()
        }
    }
}