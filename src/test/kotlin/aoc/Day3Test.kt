package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day3Test {

    @Test
    internal fun test() {
        assertEquals(8240, getRucksackPrioritiesSum())
        assertEquals(2587, getGroupBadgesSum())
    }
}