package y2024.d03

import lib.readInput

typealias Input = String
typealias Output = Int

fun main() {
    val input = readInput(2024, 3)

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Input): Output =
    Regex("mul\\((\\d+),(\\d+)\\)").findAll(input).sumOf {
        it.groupValues[1].toInt() * it.groupValues[2].toInt()
    }

fun part2(input: Input): Output {
    var enabled = true
    var res = 0
    Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)").findAll(input).forEach {
        when (it.groupValues[0]) {
            "do()" -> enabled = true
            "don't()" -> enabled = false
            else -> if (enabled) res += it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
    }
    return res
}