package y2017.d02

import lib.readInput

fun main() {
    val input = readInput(2017, 2)
        .lines()
        .map { it.split("\\s".toRegex()).map { s -> s.toInt() } }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<List<Int>>): Int
    = input.sumOf { it.max() - it.min() }

fun part2(input: List<List<Int>>): Int
    = input.sumOf {
        val (dividendIndex, divisorIndex) = it.indices.flatMap { i ->
            it.indices.map { j -> Pair(i, j) }
        }.first { (i , j) -> i != j && it[i] % it[j] == 0 }
        it[dividendIndex] / it[divisorIndex]
    }