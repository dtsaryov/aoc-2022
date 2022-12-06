package aoc

/**
 * [AoC 2022: Day 6](https://adventofcode.com/2022/day/6)
 */
fun findMarkerPosition(s: String): Int = findUniqueSeqEnd(s, 4)

fun findMessagePosition(s: String): Int = findUniqueSeqEnd(s, 14)

private fun findUniqueSeqEnd(s: String, sequenceLength: Int): Int {
    val chars = mutableSetOf<Char>()
    var position = sequenceLength - 1
    var duplicateFound = false

    do {
        for (i in (position - (sequenceLength - 1))..position) {
            duplicateFound = !chars.add(s[i])
            if (duplicateFound) break
        }

        if (!duplicateFound) break

        duplicateFound = false
        chars.clear()
        position++
    } while (true)

    return position + 1
}