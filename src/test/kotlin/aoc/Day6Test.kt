package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day6Test {

    @Test
    internal fun test() {
        assertEquals(7, findMarkerPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, findMarkerPosition("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, findMarkerPosition("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, findMarkerPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, findMarkerPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))


        assertEquals(19, findMessagePosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, findMessagePosition("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, findMessagePosition("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, findMessagePosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, findMessagePosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}