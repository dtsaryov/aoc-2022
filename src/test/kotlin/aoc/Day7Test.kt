package aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day7Test {

    @Test
    internal fun test() {
        val cmdHistory = readInput("day7_test.txt")
        assertNotNull(cmdHistory)
        assertEquals(95437, findTotalSizeOf100kDirs(cmdHistory))
        assertEquals(24933642, findDirToDelete(cmdHistory))
    }
}