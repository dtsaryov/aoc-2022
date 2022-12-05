package aoc

import kotlin.math.max
import kotlin.math.min

/**
 * [AoC 2022: Day 4](https://adventofcode.com/2022/day/4)
 */
fun getNestedRangesCount(): Int {
    val input = readInput("day4.txt") ?: return -1

    return getRanges(input).fold(0) { acc, (left, right) ->
        if (left.contains(right) || right.contains(left)) acc + 1
        else acc
    }
}

fun getOverlapsCount(): Int {
    val input = readInput("day4.txt") ?: return -1

    return getRanges(input).fold(0) { acc, (left, right) ->
        if (left.overlaps(right)) acc + 1
        else acc
    }
}

private fun getRanges(input: List<String>): List<Pair<IntRange, IntRange>> {
    return input.map { s ->
        val (l, r) = s.split(",")
        val (lf, ls) = l.split("-")
        val (rf, rs) = r.split("-")
        IntRange(lf.toInt(), ls.toInt()) to IntRange(rf.toInt(), rs.toInt())
    }
}

private fun IntRange.contains(range: IntRange): Boolean {
    return this.first <= range.first && this.last >= range.last
}

fun IntRange.overlaps(range: IntRange): Boolean {
    val lb = max(this.first, range.first)
    val rb = min(this.last, range.last)
    if (rb < lb) return false

    val overlap = IntRange(lb, rb)
    return this.contains(overlap) && range.contains(overlap)
}