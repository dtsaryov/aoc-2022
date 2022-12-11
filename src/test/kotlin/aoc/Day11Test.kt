package aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day11Test {

    @Test
    internal fun test() {
        val input = readInput("day11_test.txt")
        assertNotNull(input)

        val monkeys = parseMonkeys(input)
        assertEquals(2713310158L, sumOfTwoMostActiveMonkeyInspections(monkeys))
    }
}