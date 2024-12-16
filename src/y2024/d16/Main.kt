package y2024.d16

import lib.readInput
import lib.plus
import java.util.PriorityQueue

typealias Output = Int
typealias Point = Pair<Int, Int>

fun main() {
    val map = readInput(2024, 16).lines()
    val start = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == 'S') row to col else null } }.first()
    val end = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == 'E') row to col else null } }.first()
    val walls = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == '#') row to col else null } }.toSet()
    println("Part 1: ${part1(start, end, walls)}")
    println("Part 2: ${part2(start, end, walls)}")
}

enum class Direction(val direction: Point) {
    EAST(0 to 1) {
        override fun getRotations() = setOf(SOUTH, NORTH)
    },
    NORTH(-1 to 0) {
        override fun getRotations() = setOf(EAST, WEST)
    },
    WEST(0 to -1) {
        override fun getRotations() = setOf(SOUTH, NORTH)
    },
    SOUTH(1 to 0) {
        override fun getRotations() = setOf(EAST, WEST)
    };

    abstract fun getRotations(): Set<Direction>
}

fun part1(start: Point, end: Point, walls: Set<Point>): Output {
    val queue = PriorityQueue<Pair<Pair<Point, Direction>, Int>>(compareBy { it.second })
    queue.add((start to Direction.EAST) to 0)

    val visited = mutableMapOf<Pair<Point, Direction>, Int>()
    while (queue.isNotEmpty()) {
        val (state, score) = queue.poll()
        val (pos, dir) = state
        if (pos == end) return score

        if (state in visited && visited[state]!! < score) continue
        visited[state] = score
        val nextPos = pos + dir.direction
        if (nextPos !in walls)
            queue.add((nextPos to dir) to score + 1)
        dir.getRotations().forEach {
            queue.add((pos to it) to score + 1000)
        }
    }

    return 0
}

fun part2(start: Point, end: Point, walls: Set<Point>): Output {
    val queue = PriorityQueue<Pair<Pair<List<Point>, Direction>, Int>>(compareBy { it.second })
    queue.add((listOf(start) to Direction.EAST) to 0)

    val visited = mutableMapOf<Pair<Point, Direction>, Int>()
    var minScore = Int.MAX_VALUE
    val onMinPath = mutableSetOf<Point>()
    while (queue.isNotEmpty()) {
        val (state, score) = queue.poll()
        val (path, dir) = state
        if (path.last() == end) {
            if (score <= minScore) {
                onMinPath += path
                minScore = score
            } else {
                return onMinPath.size
            }
        }
        val pos = path.last()
        if (pos to dir in visited && visited[pos to dir]!! < score) continue
        visited[pos to dir] = score
        val nextPos = pos + dir.direction
        if (nextPos !in walls)
            queue.add((path + nextPos to dir) to score + 1)
        dir.getRotations().forEach {
            queue.add((path to it) to score + 1000)
        }
    }

    return 0
}

