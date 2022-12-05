package aoc

import java.util.*

/**
 * [AoC 2022: Day 5](https://adventofcode.com/2022/day/5)
 */
private const val SEPARATOR = "---"

fun getTopCrates(preserveOrder: Boolean): String {
    val input = readInput("day5.txt") ?: return ""

    val storage = Storage(getStorageInitialState(input), preserveOrder)
    val commands = getCommands(input)
    for (cmd in commands)
        storage.performCommand(cmd)

    return storage.getTopValues().joinToString("")
}

private fun getStorageInitialState(input: List<String>): List<List<String>> {
    val config = mutableListOf<List<String>>()
    for (s in input) {
        if (s == SEPARATOR) break
        config.add(s.split(' ').let { it.subList(1, it.size) })
    }
    return config
}

private fun getCommands(input: List<String>): List<Command> {
    return input.mapNotNull { s ->
        if (!s.startsWith("move")) return@mapNotNull null

        val parts = s.split(' ')

        Command(
            parts[1].toInt(),
            parts[3].toInt() - 1,
            parts[5].toInt() - 1
        )
    }
}

private class Storage<T>(initialState: List<List<T>>, private val preserveOrder: Boolean = false) {

    private val stacks: List<Stack<T>> = initStacks(initialState)

    private fun initStacks(initialState: List<List<T>>): List<Stack<T>> {
        return initialState.map { values ->
            Stack<T>().also { stack ->
                stack.addAll(values)
            }
        }
    }

    fun performCommand(command: Command) {
        if (preserveOrder) performCommandPreserveOrder(command)
        else performCommandInternal(command)
    }

    private fun performCommandInternal(command: Command) {
        for (i in 0 until command.amount) {
            stacks[command.to].push(stacks[command.from].pop())
        }
    }

    private fun performCommandPreserveOrder(command: Command) {
        val stack = Stack<T>()

        for (i in 0 until command.amount)
            stack.push(stacks[command.from].pop())

        for (i in 0 until command.amount)
            stacks[command.to].push(stack.pop())
    }

    fun getTopValues(): List<T> = stacks.map { it.peek() }
}

private class Command(val amount: Int, val from: Int, val to: Int)