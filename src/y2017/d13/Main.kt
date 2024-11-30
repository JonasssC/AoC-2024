package y2017.d13

import lib.readStr

fun main() {
    val input = readStr(2017, 13).lines().associate {
        val (depth, range) = it.split(": ", )
        Pair(depth.toInt(), range.toInt())
    }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Map<Int, Int>): Int =
    input.entries.filter { (depth, range) ->
        depth % (2 * (range - 1)) == 0
    }.sumOf { (depth, range) ->
        depth * range
    }

fun part2(input: Map<Int, Int>): Int {
    var offset = 0

    while (input.entries.any { (depth, range) -> (offset + depth) % (2 * (range - 1)) == 0 })
        offset++

    return offset
}