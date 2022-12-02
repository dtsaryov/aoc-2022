package aoc

/**
 * [AoC 2022: Day 2](https://adventofcode.com/2022/day/2)
 */
fun calcRockPaperScissorsScore(): Int {
    val input = readInput("day2.txt") ?: return -1

    var score = 0

    processRounds(input) { (playerShape, opponentShape) ->
        score += getPlayerRoundScore(playerShape, opponentShape)
        score += getShapeScore(playerShape)
    }

    return score
}

fun calcRockPaperScissorsScoreByChoosing(): Int {
    val input = readInput("day2.txt") ?: return -1

    var score = 0

    processRounds(input) { (roundResult, opponentShape) ->
        val playerShape = chooseShape(opponentShape, roundResult)

        score += getPlayerRoundScore(playerShape, opponentShape)
        score += getShapeScore(playerShape)
    }

    return score
}

private fun chooseShape(opponentShape: Char, roundResult: Char): Char {
    return when (roundResult) {
        'X' -> {
            when (opponentShape) {
                'A' -> 'Z'
                'B' -> 'X'
                'C' -> 'Y'
                else -> ' '
            }
        }
        'Y' -> {
            when (opponentShape) {
                'A' -> 'X'
                'B' -> 'Y'
                'C' -> 'Z'
                else -> ' '
            }
        }
        'Z' -> {
            when (opponentShape) {
                'A' -> 'Y'
                'B' -> 'Z'
                'C' -> 'X'
                else -> ' '
            }
        }
        else -> ' '
    }
}

private fun getPlayerRoundScore(playerShape: Char, opponentShape: Char): Int {
    return when (playerShape) {
        'X' -> {
            when (opponentShape) {
                'A' -> 3
                'B' -> 0
                'C' -> 6
                else -> Int.MIN_VALUE
            }
        }

        'Y' -> {
            when (opponentShape) {
                'A' -> 6
                'B' -> 3
                'C' -> 0
                else -> Int.MIN_VALUE
            }
        }

        'Z' -> {
            when (opponentShape) {
                'A' -> 0
                'B' -> 6
                'C' -> 3
                else -> Int.MIN_VALUE
            }
        }

        else -> Int.MIN_VALUE
    }
}

private fun getShapeScore(shape: Char): Int {
    return when (shape) {
        'X' -> 1
        'Y' -> 2
        'Z' -> 3
        else -> Int.MIN_VALUE
    }
}

private fun processRounds(input: List<String>, processor: (Pair<Char, Char>) -> Unit) {
    for (line in input) {
        processor(line[2] to line[0])
    }
}