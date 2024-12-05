package y2024.d01

import lib.readInput
import kotlin.math.abs

fun main() {
    val (list1, list2) = readInput(2024, 1).lines().fold(Pair<MutableList<Int>, MutableList<Int>>(mutableListOf(), mutableListOf())) { (list1, list2), line ->
        val split = line.split(Regex("\\s+"))
        list1.add(split[0].toInt())
        list2.add(split[1].toInt())
        list1 to list2
    }

    println("Part 1: ${part1(list1, list2)}")
    println("Part 2: ${part2(list1, list2)}")
}

fun part1(list1: List<Int>, list2: List<Int>): Int {
    val list1Sorted = list1.sorted()
    val list2Sorted = list2.sorted()

    var res = 0
    for (i in list1Sorted.indices) {
        res += abs(list1Sorted[i] - list2Sorted[i])
    }

    return res
}

fun part2(list1: List<Int>, list2: List<Int>): Int =
    list1.sumOf {
        it * list2.count { x -> x == it }
    }
