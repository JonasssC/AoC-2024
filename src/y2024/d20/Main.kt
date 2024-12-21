package y2024.d20

import lib.readInput
import lib.plus
import kotlin.math.abs

typealias Output = Int
typealias Input = List<String>
typealias Point = Pair<Int, Int>

fun main() {
    val input = readInput(2024, 20).lines()
    val start = input.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == 'S') row to col else null } }.first()
    val end = input.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == 'E') row to col else null } }.first()
    val open = input.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == '.') row to col else null } }.toSet()
    println("Part 1: ${part1(start, end, open)}")
    println("Part 2: ${part2(start, end, open)}")
}

val directions = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)

fun getPath(start: Point, end: Point, open: Set<Point>): List<Point> {
    val path = mutableListOf(start)
    while (true) {
        val nextPos = directions.map {
            it + path.last()
        }.first {
            it !in path && (it in open || it == end)
        }
        if (nextPos == end) return path + end
        path.add(nextPos)
    }
}

fun part1(start: Point, end: Point, open: Set<Point>): Output {
    val path = getPath(start, end, open)
    var res = 0
    for (i in 0..<path.size - 100) {
        for (j in i + 101..<path.size) {
            val manhattanDistance = abs(path[i].first - path[j].first) + abs(path[i].second - path[j].second)
            if (manhattanDistance == 2) {
                res++
            }
        }
    }
    return res
}

fun part2(start: Point, end: Point, open: Set<Point>): Long {
    val path = getPath(start, end, open)
    var res = 0L
    for (i in 0..<path.size - 100) {
        for (j in i + 81..<path.size) {
            val manhattanDistance = abs(path[i].first - path[j].first) + abs(path[i].second - path[j].second)
            if (manhattanDistance <= 20 && j - i - manhattanDistance >= 100) {
                res++
            }
        }
    }
    return res
}
