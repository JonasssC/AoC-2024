package y2024.d07

import lib.readInput
import kotlin.time.measureTime

typealias Output = Long
typealias Input = List<Pair<Long, List<Long>>>

fun main() {
    val input = readInput(2024, 7).lines().map {
        val split = it.split(": ", " ")
        split[0].toLong() to split.subList(1, split.size).map { d -> d.toLong() }
    }

    val time1 = measureTime {
        println("Part 1: ${part1(input)}")
    }
    println("Part 1 took $time1")
    val time2 = measureTime {
        println("Part 2: ${part2(input)}")
    }
    println("Part 2 took $time2")
}

enum class Operation {
    MULTIPLY {
        override fun apply(a: Long, b: Long): Long =
            a * b
    },
    ADD {
        override fun apply(a: Long, b: Long): Long =
            a + b
    },
    CONCAT {
        override fun apply(a: Long, b: Long): Long =
            "$a$b".toLong()
    };

    abstract fun apply(a: Long, b: Long): Long
}

fun isPossible(values: List<Long>, result: Long, ops: Set<Operation>): Boolean {
    if (values.isEmpty()) return false
    if (values[0] > result) return false
    if (values.size == 1) return values[0] == result
    return ops.any {
        isPossible(listOf(it.apply(values[0], values[1]), *values.subList(2, values.size).toTypedArray()), result, ops)
    }
}

fun part1(input: Input): Output =
    input.filter {
        isPossible(it.second, it.first, setOf(Operation.MULTIPLY, Operation.ADD))
    }.sumOf { it.first }

fun part2(input: Input): Output =
    input.filter {
        isPossible(it.second, it.first, Operation.entries.toSet())
    }.sumOf { it.first }
