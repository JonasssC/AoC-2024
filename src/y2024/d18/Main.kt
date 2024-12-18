package y2024.d18

import lib.readInput
import lib.plus

typealias Output = Int
typealias Input = List<Pair<Int, Int>>

const val DIM = 71
const val N = 1024
val START = 0 to 0
val END = 70 to 70

fun main() {
    val input = readInput(2024, 18).lines().map {
        val split = it.split(",")
        split[0].toInt() to split[1].toInt()
    }

    println("Part 1: ${part1(input.take(N).toSet())}")
    println("Part 2: ${part2(input)}")
}

val directions = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)

fun inField(coord: Pair<Int, Int>): Boolean {
    return 0 <= coord.first && coord.first < DIM && 0 <= coord.second && coord.second < DIM
}

fun heuristic(point: Pair<Int, Int>): Int {
    return END.first - point.first + END.second - point.second
}

fun part1(input: Set<Pair<Int, Int>>): Output {
    val openSet = mutableSetOf(START)
    val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
    val gScore = mutableMapOf(START to 0)
    val fScore = mutableMapOf(START to heuristic(START))

    while (openSet.isNotEmpty()) {
        val current = openSet.minBy { fScore[it]!! }
        if (current == END) {
            return gScore[END]!!
        }

        openSet.remove(current)
        for (direction in directions) {
            val neighbour = current + direction
            if (!inField(neighbour) || neighbour in input) continue
            val tentativeGScore = gScore[current]!! + 1
            if (neighbour !in gScore || tentativeGScore < gScore[neighbour]!!) {
                cameFrom[neighbour] = current
                gScore[neighbour] = tentativeGScore
                fScore[neighbour] = tentativeGScore + heuristic(neighbour)
                if (neighbour !in openSet)
                    openSet.add(neighbour)
            }
        }
    }

    return -1
}

fun part2(input: Input): String {
    var min = N + 1
    var max = input.size - 1
    while (min < max) {
        val avg = (min + max) / 2
        if (part1(input.take(avg + 1).toMutableSet()) == -1) {
            max = avg
        } else {
            min = avg + 1
        }
    }
    return "${input[min].first},${input[min].second}"
}
