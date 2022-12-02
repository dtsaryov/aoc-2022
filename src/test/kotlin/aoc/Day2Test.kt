package aoc

import org.junit.Test
import kotlin.test.assertEquals

internal class Day2Test {

    @Test
    internal fun test() {
        assertEquals(13009, calcRockPaperScissorsScore())
        assertEquals(10398, calcRockPaperScissorsScoreByChoosing())
    }
}