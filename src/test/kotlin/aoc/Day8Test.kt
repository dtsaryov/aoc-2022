package aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day8Test {

    @Test
    internal fun test() {
        val input = readInput("day8_test.txt")
        assertNotNull(input)
        assertEquals(21, amountOfVisibleTrees(input))
        assertEquals(8, highestScenicScore(input))
    }
}