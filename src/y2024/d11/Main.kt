package y2024.d11

import lib.readInput
import kotlin.time.measureTime

typealias Output = Long
typealias Input = List<Long>

fun main() {
    val input = readInput(2024, 11).split(" ").map { it.toLong() }

    val time1 = measureTime {
        println("Part 1: ${part1(input)}")
    }
    println("Part 1 took $time1")
    val time2 = measureTime {
        println("Part 2: ${part2(input)}")
    }
    println("Part 2 took $time2")
}

val countSplitsCache = mutableMapOf<Pair<Long, Int>, Long>()
fun countSplits(stone: Long, blinksLeft: Int): Long =
    countSplitsCache.getOrPut(stone to blinksLeft) {
        when {
            blinksLeft == 0 -> 1
            stone == 0L -> {
                countSplits(1, blinksLeft - 1)
            }
            "$stone".length % 2 == 0 -> {
                "$stone".chunked("$stone".length / 2).sumOf { s ->
                    countSplits(s.toLong(), blinksLeft - 1)
                }
            }
            else -> {
                countSplits(stone * 2024, blinksLeft - 1)
            }
        }
    }

fun part1(input: Input): Output =
    input.sumOf { countSplits(it, 25) }

fun part2(input: Input): Output =
    input.sumOf { countSplits(it, 75) }