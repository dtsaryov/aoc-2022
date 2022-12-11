package aoc

private val checkTicks = listOf(20, 60, 100, 140, 180, 220)
private val screenLines = listOf(40, 80, 120, 160, 200, 240)

/**
 * [AoC 2022: Day 10](https://adventofcode.com/2022/day/10)
 */
fun displayInput(input: List<String>) {
    var signal = 1
    var tick = 1
    var line = 0

    for (s in input) {
        val (op, v) = parseCommand(s)

        for (t in 0 until op.ticks) {
            drawPixel(signal, tick, line)

            tick++

            if (tick - 1 in screenLines)
                line++
        }

        if (op == Op.Add)
            signal += v
    }
}

private fun drawPixel(signal: Int, tick: Int, line: Int) {
    if (tick - 1 in screenLines)
        println()

    val idx = tick - 40 * line
    val sprite = listOf(signal - 1, signal, signal + 1)

    if (idx - 1 in sprite)
        print("#")
    else
        print(".")
}

fun getSignalStrengthSum(input: List<String>): Int {
    var signal = 1
    var sum = 0

    var tick = 1

    for (s in input) {
        val (op, v) = parseCommand(s)

        for (t in 0 until op.ticks) {
            if (tick in checkTicks) {
                sum += signal * tick
            }
            tick++
        }

        if (op == Op.Add) {
            signal += v
        }
    }

    return sum
}

private fun parseCommand(s: String): Pair<Op, Int> {
    if (s == "noop") return Op.Noop to 0
    val (_, v) = s.split(" ")
    return Op.Add to v.toInt()
}

private enum class Op(val ticks: Int) { Noop(1), Add(2) }