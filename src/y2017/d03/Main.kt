package y2017.d03

import kotlin.math.abs

fun main() {
    val input = 277678
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Int): Int {
    var limit = 1
    var dif = 8
    var width = 1
    while (input > limit) {
        limit += dif
        dif += 8
        width += 2
    }

    val prevWidth = width - 2
    val x = input - prevWidth * prevWidth
    val halfWidth = width / 2

    return halfWidth + abs(x % (width-1) - halfWidth)
}

fun part2(input: Int): Int {
    val map = mutableMapOf(Pair(0, 0) to 1).withDefault { 0 }
    var distanceFromCenter = 0
    var x = 0
    var y = 0

    while (map.getValue(Pair(x, y)) <= input) {
        if (y == distanceFromCenter) {
            if (x == distanceFromCenter) {
                distanceFromCenter++
            }
            x++
        } else if (x == -distanceFromCenter) {
            y++
        } else if (y == -distanceFromCenter) {
            x--
        } else if (x == distanceFromCenter) {
            y--
        }

        map[Pair(x, y)] = (-1..1)
            .flatMap { dx -> (-1..1).map { dy -> Pair( x + dx, y + dy) } }
            .sumOf { map.getValue(it) }
    }

    return map.getValue(Pair(x, y))
}