package aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day9Test {

    @Test
    internal fun test() {
        val input = readInput("day9_test_1.txt")
        assertNotNull(input)
        assertEquals(13, getRopeTailPositionsCount(input, 2))
        assertEquals(1, getRopeTailPositionsCount(input, 10))

        val input2 = readInput("day9_test_2.txt")
        assertNotNull(input2)
        assertEquals(36, getRopeTailPositionsCount(input2, 10))
    }
}