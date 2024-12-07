package y2024.d06

import lib.readInput
import kotlin.time.measureTime

typealias Output = Int
typealias Point = Pair<Int, Int>
typealias Guard = Pair<Point, Direction>

fun main() {
    val input = readInput(2024, 6).lines()

    val dim = input.first().length to input.size
    val obstacles = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c  ->
            if (c == '#') {
                x to y
            } else {
                null
            }
        }
    }.toSet()
    val (guardY, guardLine) = input.withIndex().first {
        it.value.contains(Regex("[<>^v]"))
    }
    val (guardX, guardChar) = guardLine.withIndex().first {
        it.value in listOf('<', '>', 'v', '^')
    }
    val guard = (guardX to guardY) to Direction.identify(guardChar)

    val time1 = measureTime {
        println("Part 1: ${part1(dim, obstacles, guard)}")
    }
    println("Part 1 took $time1")
    val time2 = measureTime {
        println("Part 2: ${part2(dim, obstacles, guard)}")
    }
    println("Part 2 took $time2")
}

enum class Direction(private val id: Char, private val step: Pair<Int, Int>) {
    UP('^', 0 to -1) {
        override fun rotate(): Direction = RIGHT
    },
    DOWN('v', 0 to 1) {
        override fun rotate(): Direction = LEFT
    },
    RIGHT('>', 1 to 0) {
        override fun rotate(): Direction = DOWN
    },
    LEFT('<', -1 to 0) {
        override fun rotate(): Direction = UP
    };

    abstract fun rotate(): Direction

    fun step(pos: Point): Point = pos.first + step.first to pos.second + step.second

    companion object {
        fun identify(c: Char): Direction = entries.first { it.id == c }
    }
}

fun guardIsInField(dim: Pair<Int, Int>, coords: Point): Boolean =
    0 <= coords.first && coords.first < dim.first && 0 <= coords.second && coords.second < dim.second

fun stepAround(dim: Pair<Int, Int>, obstacles: Set<Pair<Int,Int>>, guard: Guard, stepped: MutableSet<Guard> = mutableSetOf()): Pair<Guard, Set<Guard>> {
    var movingGuard = guard

    while (guardIsInField(dim, movingGuard.first) && movingGuard !in stepped) {
        stepped.add(movingGuard)
        val nextPos = movingGuard.second.step(movingGuard.first)
        movingGuard = if (nextPos in obstacles) {
            movingGuard.first to movingGuard.second.rotate()
        } else {
            nextPos to movingGuard.second
        }
    }

    return movingGuard to stepped
}

fun part1(dim: Pair<Int, Int>, obstacles: Set<Pair<Int,Int>>, guard: Guard): Output =
    stepAround(dim, obstacles, guard).second.distinctBy { it.first }.count()

fun part2(dim: Pair<Int, Int>, obstacles: Set<Pair<Int,Int>>, guard: Guard): Output {
    var movingGuard = guard
    val stepped: MutableSet<Guard> = mutableSetOf()
    val testedPositions: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var count = 0

    while (guardIsInField(dim, movingGuard.first) && movingGuard !in stepped) {
        val nextPos = movingGuard.second.step(movingGuard.first)
        if (nextPos !in testedPositions && nextPos != guard.first && guardIsInField(dim, nextPos)) {
            val newObstacles = obstacles.toMutableSet()
            newObstacles.add(nextPos)
            testedPositions.add(nextPos)
            val (finalGuard, _) = stepAround(dim, newObstacles, movingGuard, stepped.toMutableSet())
            if (guardIsInField(dim, finalGuard.first)) {
                count++
            }
        }
        stepped.add(movingGuard)
        movingGuard = if (nextPos in obstacles) {
            movingGuard.first to movingGuard.second.rotate()
        } else {
            nextPos to movingGuard.second
        }
    }

    return count
}
