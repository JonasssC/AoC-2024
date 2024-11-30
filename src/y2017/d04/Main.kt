package y2017.d04

import lib.readStr

fun main() {
    val input = readStr(2017, 4)
        .lines()
        .map { it.split("\\s".toRegex()) }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<List<String>>): Int
    = input.filter {
        it.size == it.toSet().size
    }.size

fun part2(input: List<List<String>>): Int
    = input.filter {
        it.indices.none { i ->
            it.subList(i+1, it.size)
                .any { word -> it[i].toSet() == word.toSet() }
        }
    }.size