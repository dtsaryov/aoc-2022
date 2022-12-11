package aoc

import java.math.BigInteger
import java.util.*

private val operationRegex = Regex("\\s*Operation: new = (old|\\d+) ([+\\-*/]) (old|\\d+)")

private const val ROUNDS = 10_000

fun main() {
    val input = readInput("day11.txt") ?: return
    val monkeys = parseMonkeys(input)
    println(sumOfTwoMostActiveMonkeyInspections(monkeys))
}

fun sumOfTwoMostActiveMonkeyInspections(monkeys: List<Monkey>): Long {
    val lcm = monkeys.map { it.divider }.reduce(BigInteger::times)
    val dividers = monkeys.map { it.divider }

    for (round in 0 until ROUNDS) {
        for ((i, monkey) in monkeys.withIndex()) {
            while (monkey.items.isNotEmpty()) {
                val item = monkey.inspect()?.mod(lcm) ?: continue
                if (item.mod(dividers[i]) == BigInteger.ZERO) {
                    monkeys[monkey.trueMonkey].take(item)
                } else {
                    monkeys[monkey.falseMonkey].take(item)
                }
            }
        }
    }
    return monkeys.map { it.inspections }
        .sortedByDescending { it }
        .subList(0, 2)
        .reduce(Long::times)
}

fun parseMonkeys(input: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()

    for (i in input.indices) {
        val line = input[i]
        if (line.startsWith("Monkey ")) {
            monkeys += parseMonkey(input.subList(i + 1, i + 6))
        }
    }

    return monkeys
}

fun parseMonkey(lines: List<String>): Monkey {
    val items = parseItems(lines[0])
    val operation = parseOperation(lines[1])
    val divider = lines[2].split(" ").last().toBigInteger()
    val trueMonkey = lines[3].split(" ").last().toInt()
    val falseMonkey = lines[4].split(" ").last().toInt()
    return Monkey(items, operation, divider, trueMonkey, falseMonkey)
}

private fun parseItems(items: String): Queue<BigInteger> {
    return items.substringAfter(":")
        .trim()
        .split(", ")
        .map { it.toBigInteger() }
        .let { LinkedList(it) }
}

private fun parseOperation(operation: String): (BigInteger) -> BigInteger {
    val (_, a, op, b) = operationRegex.matchEntire(operation)
        ?.groupValues ?: return { it }

    val operator = parseOp(op)

    val numA = a.toBigIntegerOrNull()
    val numB = b.toBigIntegerOrNull()

    return when {
        a == "old" && b == "old" -> { v -> operator.invoke(v, v) }
        a == "old" -> { v -> operator.invoke(v, numB!!) }
        b == "old" -> { v -> operator.invoke(numA!!, v) }
        else -> error("impossible branch")
    }
}

private fun parseOp(op: String): (BigInteger, BigInteger) -> BigInteger {
    return when (op) {
        "+" -> BigInteger::plus
        "-" -> BigInteger::minus
        "*" -> BigInteger::times
        "/" -> BigInteger::div
        else -> error("wrong operator")
    }
}

data class Monkey(
    val items: Queue<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val divider: BigInteger,
    val trueMonkey: Int,
    val falseMonkey: Int
) {

    var inspections: Long = 0
        private set

    fun inspect(): BigInteger? {
        return if (items.isEmpty()) {
            null
        } else {
            inspections++
            operation.invoke(items.poll())
        }
    }

    fun take(item: BigInteger) {
        items.add(item)
    }
}