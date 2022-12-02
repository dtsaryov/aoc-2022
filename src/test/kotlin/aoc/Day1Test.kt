package aoc

import org.junit.Test
import kotlin.test.assertEquals

internal class Day1Test {

    @Test
    internal fun test() {
        assertEquals(70374, findMaxCalories())
        assertEquals(204610, findSumOfTop3Calories())
    }
}