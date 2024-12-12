package y2024.d12

import lib.readInput

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

val directions = listOf(0 to 1, -1 to 0, 1 to 0, 0 to -1)

fun collectGroup(coord: Pair<Int, Int>, coords: MutableMap<Pair<Int, Int>, Char>): Set<Pair<Int, Int>> {
    if (!coords.containsKey(coord)) return emptySet()
    val char = coords[coord]!!
    coords -= coord
    val result = mutableSetOf(coord)
    for (direction in directions) {
        val newCoord = coord.first + direction.first to coord.second + direction.second
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

enum class Side(val direction: Pair<Int, Int>) {
    LEFT(0 to -1), RIGHT(0 to 1), TOP(-1 to 0), BOTTOM(1 to 0);
}

fun collectSides(area: Set<Pair<Int, Int>>): List<Pair<Pair<Int, Int>, Side>> =
    area.flatMap {
        Side.entries.mapNotNull { side ->
            val newCoord = it.first + side.direction.first to it.second + side.direction.second
            if (newCoord !in area) {
                newCoord to side
            } else {
                null
            }
        }
    }

fun part1(coords: Input): Output =
    collectGroups(coords).sumOf {
        collectSides(it).size * it.size
    }

fun part2(coords: Input): Output =
    collectGroups(coords).sumOf {
        val sides = collectSides(it)
        val uniqueSides = sides.filterNot { (coord, dir) ->
            val coordToCheck = when (dir) {
                Side.LEFT, Side.RIGHT -> coord.first - 1 to coord.second
                Side.TOP, Side.BOTTOM -> coord.first to coord.second - 1
            }
            sides.contains(coordToCheck to dir)
        }
        uniqueSides.size * it.size
    }