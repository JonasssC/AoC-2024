package y2017.d01

import lib.readStr

fun main() {
    val input = readStr(2017, 1)
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int
    = solve(input, 1)

fun part2(input: String): Int
        = solve(input, input.length / 2)

fun solve(input: String, shift: Int): Int {
    var sum = 0
    for (i in input.indices) {
        sum += if (input[i] == input[(i + shift) % input.length]) input[i].digitToInt() else 0
    }
    return sum
}