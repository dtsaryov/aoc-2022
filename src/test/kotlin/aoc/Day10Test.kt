package aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day10Test {

    @Test
    internal fun test() {
        val input = readInput("day10_test.txt")
        assertNotNull(input)
        assertEquals(13140, getSignalStrengthSum(input))
        displayInput(input)
    }
}