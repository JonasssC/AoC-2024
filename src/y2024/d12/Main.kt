package y2024.d12

import lib.readInput
import lib.plus

typealias Output = Int
typealias Input = Map<Pair<Int, Int>, Char>

fun main() {
    val input = readInput(2024, 12).lines().flatMapIndexed { row, line ->
        line.mapIndexed { col, char ->
            (row to col) to char
        }
    }.toMap()

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

fun collectGroup(coord: Pair<Int, Int>, coords: MutableMap<Pair<Int, Int>, Char>): Set<Pair<Int, Int>> {
    if (!coords.containsKey(coord)) return emptySet()
    val char = coords[coord]!!
    coords -= coord
    val result = mutableSetOf(coord)
    for (direction in directions) {
        val newCoord = coord + direction
        if (newCoord in coords && coords[newCoord] == char) {
            result += collectGroup(newCoord, coords)
        }
    }
    return result
}

fun collectGroups(coords: Input): Set<Set<Pair<Int, Int>>> {
    val coordsToBeChecked = coords.toMutableMap()
    val res = mutableSetOf<Set<Pair<Int, Int>>>()
    while (coordsToBeChecked.isNotEmpty()) {
        res += collectGroup(coordsToBeChecked.keys.first(), coordsToBeChecked)
    }
    return res
}
fun calculatePerimeter(area: Set<Pair<Int, Int>>): Int =
    area.sumOf {
        directions.count { dir ->
            it + dir !in area
        }
    }

fun part1(coords: Input): Output =
    collectGroups(coords).sumOf {
        calculatePerimeter(it) * it.size
    }

val cornerChecks = listOf(
    directions[0] to directions[1],
    directions[1] to directions[2],
    directions[2] to directions[3],
    directions[3] to directions[0]
)

fun countCorners(area: Set<Pair<Int, Int>>): Int =
    area.sumOf {
        cornerChecks.count { (dir1, dir2) ->
            (it + dir1 !in area && it + dir2 !in area) // 90° corner
                    || (it + dir1 in area && it + dir2 in area && it + dir1 + dir2 !in area) // 270° corner
        }
    }

fun part2(coords: Input): Output =
    collectGroups(coords).sumOf {
        countCorners(it) * it.size
    }