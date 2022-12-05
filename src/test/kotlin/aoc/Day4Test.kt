package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day4Test {

    @Test
    internal fun test() {
        assertEquals(498, getNestedRangesCount())
        assertEquals(859, getOverlapsCount())
    }
}