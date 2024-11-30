package y2017.d05

import lib.readStr

fun main() {
    val input = readStr(2017, 5)
        .lines()
        .map { it.toInt() }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<Int>): Int {
    val instructions = input.toMutableList()
    var steps = 0
    var pointer = 0

    while (0 <= pointer && pointer < instructions.size) {
        val dif = instructions[pointer]
        instructions[pointer]++
        pointer += dif
        steps++
    }

    return steps
}

fun part2(input: List<Int>): Int {
    val instructions = input.toMutableList()
    var steps = 0
    var pointer = 0

    while (0 <= pointer && pointer < instructions.size) {
        val dif = instructions[pointer]
        if (dif >= 3)
            instructions[pointer]--
        else
            instructions[pointer]++
        pointer += dif
        steps++
    }

    return steps
}
