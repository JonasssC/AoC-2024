package y2024.d05

import lib.readInput

// typealias Input = List<String>
typealias Output = Int

fun main() {
    val input = readInput(2024, 5).split("\n\n")
    val rules = input[0].lines().map {
        val split = it.split('|')
        split[0].toInt() to split[1].toInt()
    }
    val updates = input[1].lines().map {
        it.split(',').map { d -> d.toInt() }
    }

    println("Part 1: ${part1(rules, updates)}")
    println("Part 2: ${part2(rules, updates)}")
}

fun compare(a: Int, b: Int, rules: List<Pair<Int, Int>>): Int =
    if (a to b in rules) {
        -1
    } else if (b to a in rules) {
        1
    } else {
        0
    }

fun part1(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Output =
    updates.filter {
        it == it.sortedWith { a, b -> compare(a, b, rules) }
    }.sumOf { it[it.size / 2] }

fun part2(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Output =
    updates.map {
        val sorted = it.sortedWith { a, b -> compare(a, b, rules) }
        if (sorted == it) listOf(0) else sorted
    }.sumOf { it[it.size / 2] }
