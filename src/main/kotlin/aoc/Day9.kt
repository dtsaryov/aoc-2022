package aoc

import kotlin.math.abs
import kotlin.math.hypot

/**
 * [AoC 2022: Day 9](https://adventofcode.com/2022/day/9)
 */
fun getRopeTailPositionsCount(moves: List<String>, length: Int): Int {
    val points = IntRange(0, length - 1)
        .map { Point(1, 1) }
        .toTypedArray()

    var head: Point
    var tail: Point

    val positions = mutableSetOf(points[0])

    for (move in moves) {
        val (direction, distance) = parseMove(move)

        for (d in 1..distance) {
            head = points[0]
            head = moveHead(head, direction)
            points[0] = head

            for (i in 1 until points.size) {
                tail = points[i]

                if (shouldMoveTail(head, tail)) {
                    tail = moveTail(tail, head)
                    points[i] = tail

                    if (i == length - 1) {
                        positions += tail
                    }
                }

                head = points[i]
            }
        }
    }

    return positions.size
}

private fun moveHead(head: Point, direction: Direction): Point {
    return when (direction) {
        Direction.UP -> Point(head.x, head.y + 1)
        Direction.RIGHT -> Point(head.x + 1, head.y)
        Direction.DOWN -> Point(head.x, head.y - 1)
        Direction.LEFT -> Point(head.x - 1, head.y)
    }
}

private fun parseMove(move: String): Move {
    val (direction, distance) = move.split(" ")
    return Move(Direction.fromKey(direction), distance.toInt())
}

private fun shouldMoveTail(head: Point, tail: Point): Boolean {
    val (hx, hy) = head
    val (tx, ty) = tail
    return hypot((hx - tx).toDouble(), (hy - ty).toDouble()) >= 2
}

private fun moveTail(tail: Point, head: Point): Point {
    val (tx, ty) = tail
    val (hx, hy) = head

    var dx = 0
    var dy = 0

    if (tx == hx) {
        dy = pointNearby(hy, ty)
    } else if (ty == hy) {
        dx = pointNearby(hx, tx)
    } else {
        if (abs(hx - tx) < abs(hy - ty)) {
            dx = hx - tx
            dy = pointNearby(hy, ty)
        } else if (abs(hx - tx) > abs(hy - ty)) {
            dx = pointNearby(hx, tx)
            dy = hy - ty
        } else {
            dx = pointNearby(hx, tx)
            dy = pointNearby(hy, ty)
        }
    }

    return Point(tx + dx, ty + dy)
}

private fun pointNearby(h: Int, t: Int): Int = h - t - sign(h - t)

private fun sign(n: Int): Int = if (n >= 0) 1 else -1

private data class Move(val direction: Direction, val distance: Int)

private data class Point(val x: Int, val y: Int)

private enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    companion object {
        fun fromKey(key: String): Direction {
            return values().find { it.name.startsWith(key) }!!
        }
    }
}