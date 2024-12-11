package y2024.d10

import lib.readInput
import kotlin.time.measureTime

typealias Output = Int
typealias Input = List<String>

fun main() {
    val input = readInput(2024, 10).lines()

    val time1 = measureTime {
        println("Part 1: ${part1(input)}")
    }
    println("Part 1 took $time1")
    val time2 = measureTime {
        println("Part 2: ${part2(input)}")
    }
    println("Part 2 took $time2")
}

val directions = listOf(-1 to 0, 1 to 0, 0 to 1, 0 to -1)

fun inField(input: Input, coord: Pair<Int, Int>): Boolean =
    0 <= coord.first && coord.first < input.size && 0 <= coord.second && coord.second < input[coord.first].length

fun findReachableNines(input: List<String>, coord: Pair<Int, Int>): List<Pair<Int, Int>> {
    val current = input[coord.first][coord.second]
    if (current == '9') return listOf(coord)

    var res = listOf<Pair<Int, Int>>()
    for ((dRow, dCol) in directions) {
        val newCoord = coord.first + dRow to coord.second + dCol
        if (inField(input, newCoord)
            && input[newCoord.first][newCoord.second] == current + 1) {
            res = res + findReachableNines(input, newCoord)
        }
    }
    return res
}

fun part1(input: Input): Output =
    input.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, c -> if (c == '0') row to col else null }
    }.sumOf {
        findReachableNines(input, it).distinct().size
    }

fun part2(input: Input): Output =
    input.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, c -> if (c == '0') row to col else null }
    }.sumOf {
        findReachableNines(input, it).size
    }
