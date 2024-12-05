package y2024.d02

import lib.readInput
import kotlin.math.abs

fun main() {
    val input = readInput(2024, 2).lines().map { it.split(" ").map { n -> n.toInt() } }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<List<Int>>): Int =
    input.count {
        (it == it.sorted() || it == it.sortedDescending())
                && it.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 }
    }

fun part2(input: List<List<Int>>): Int =
    input.count {
        (0..it.size).map { i ->
            it.filterIndexed { j, _ -> j != i }
        }.any { l ->
            (l == l.sorted() || l == l.sortedDescending())
                    && l.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 }
        }
    }
