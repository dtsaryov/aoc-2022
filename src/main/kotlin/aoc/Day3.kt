package aoc

/**
 * [AoC 2022: Day 3](https://adventofcode.com/2022/day/3)
 */
fun getRucksackPrioritiesSum(): Int {
    val input = readInput("day3.txt") ?: return Int.MIN_VALUE

    var sum = 0
    for (s in input) {
        sum += collectDuplicates(s).fold(0) { acc, ch -> acc + getCharPriority(ch) }
    }

    return sum
}

fun getGroupBadgesSum(): Int {
    val input = readInput("day3.txt") ?: return Int.MIN_VALUE

    var sum = 0
    for (i in input.indices step 3) {
        val strings = listOf(input[i], input[i + 1], input[i + 2])
        sum += getCharPriority(findDuplicate(strings))
    }

    return sum
}

private fun findDuplicate(strings: List<String>): Char {
    val chars = mutableMapOf<Char, Int>()

    for ((i, s) in strings.withIndex()) {
        for (ch in s) {
            chars.compute(ch) { _, count ->
                if (count == null) {
                    if (i == 0) 1
                    else 0
                } else {
                    when (i) {
                        0 -> count
                        1 -> if (count == 1) 2 else count
                        2 -> if (count == 2) 3 else count
                        else -> 0
                    }
                }
            }
        }
    }

    return chars.keys.find { ch -> chars[ch] == 3 }!!
}

private fun collectDuplicates(s: String): List<Char> {
    val chars = mutableMapOf<Char, Int>()

    for (i in s.indices) {
        val c = s[i]
        chars.compute(c) { _, count ->
            if (i < s.length / 2) {
                1
            } else {
                if (count == null) null
                else count + 1
            }
        }
    }

    return chars.keys.mapNotNull { char ->
        val count = chars[char]
        if (count == null || count < 2) null
        else char
    }
}

private fun getCharPriority(char: Char): Int {
    return if (char.isLowerCase())
        char.code - 96
    else
        char.code - 38
}