package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day5Test {

    @Test
    internal fun test() {
        assertEquals("RTGWZTHLD", getTopCrates(false))
        assertEquals("STHGRZZFR", getTopCrates(true))
    }
}