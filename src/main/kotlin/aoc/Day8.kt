package aoc

/**
 * [AoC 2022: Day 8](https://adventofcode.com/2022/day/8)
 */
fun amountOfVisibleTrees(input: List<String>): Int {
    val grid = parseGrid(input)

    val visibleTrees = mutableSetOf<Pair<Int, Int>>()

    walk(grid) { tree, i, j ->
        if (visibleFromTop(tree, i, j, grid))
            visibleTrees += i to j

        if (visibleFromRight(tree, i, j, grid))
            visibleTrees += i to j

        if (visibleFromBottom(tree, i, j, grid))
            visibleTrees += i to j

        if (visibleFromLeft(tree, i, j, grid))
            visibleTrees += i to j
    }

    return visibleTrees.size
}

fun highestScenicScore(input: List<String>): Int {
    val grid = parseGrid(input)

    var maxScore = 0

    walk(grid) { tree, i, j ->
        val treeScore = getTopScore(tree, i, j, grid) *
                getRightScore(tree, i, j, grid) *
                getBottomScore(tree, i, j, grid) *
                getLeftScore(tree, i, j, grid)

        if (treeScore > maxScore) maxScore = treeScore
    }

    return maxScore

}

private fun visibleFromTop(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Boolean {
    if (i == 0) return true

    var highest = -1
    for (ii in i - 1 downTo 0) {
        if (highest < grid[ii][j])
            highest = grid[ii][j]
    }
    return highest < tree
}

private fun visibleFromRight(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Boolean {
    if (j == grid.size - 1) return true

    var highest = -1
    for (jj in j + 1 until grid.size) {
        if (highest < grid[i][jj])
            highest = grid[i][jj]
    }
    return highest < tree
}

private fun visibleFromBottom(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Boolean {
    if (i == grid.size - 1) return true

    var highest = -1
    for (ii in i + 1 until grid.size) {
        if (highest < grid[ii][j])
            highest = grid[ii][j]
    }
    return highest < tree
}

private fun visibleFromLeft(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Boolean {
    if (j == 0) return true

    var highest = -1
    for (jj in j - 1 downTo 0) {
        if (highest < grid[i][jj])
            highest = grid[i][jj]
    }
    return highest < tree
}

private fun getTopScore(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Int {
    if (i == 0) return 0

    var score = 0
    for (ii in i - 1 downTo 0) {
        score++
        if (tree <= grid[ii][j]) break
    }
    return score
}

private fun getRightScore(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Int {
    if (j == grid.size - 1) return 0

    var score = 0
    for (jj in j + 1 until grid.size) {
        score++
        if (tree <= grid[i][jj]) break
    }
    return score
}

private fun getBottomScore(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Int {
    if (i == grid.size - 1) return 0

    var score = 0
    for (ii in i + 1 until grid.size) {
        score++
        if (tree <= grid[ii][j]) break
    }
    return score
}

private fun getLeftScore(tree: Int, i: Int, j: Int, grid: Array<Array<Int>>): Int {
    if (j == 0) return 0

    var score = 0
    for (jj in j - 1 downTo 0) {
        score++
        if (tree <= grid[i][jj]) break
    }
    return score
}

@Suppress("UNCHECKED_CAST")
private fun parseGrid(input: List<String>): Array<Array<Int>> {
    val grid = arrayOfNulls<Array<Int>>(input.size)
    for ((i, s) in input.withIndex()) {
        val row = arrayOfNulls<Int>(s.length)
        for ((j, ch) in s.withIndex())
            row[j] = ch.digitToInt()

        grid[i] = row as Array<Int>
    }
    return grid as Array<Array<Int>>
}

private fun <T> walk(grid: Array<Array<T>>, visitor: (T, Int, Int) -> Unit) {
    for (i in grid.indices)
        for (j in grid.indices)
            visitor(grid[i][j], i, j)
}