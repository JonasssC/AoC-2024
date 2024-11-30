package y2017.d14

import y2017.d10.knotHash

fun main() {
    val input = "ljoxqyyw"
    val grid = (0..127)
        .map {
            knotHash("$input-$it").chunked(4)
                .joinToString("") { chunk ->
                    chunk.toInt(16).toString(2).padStart(16, '0')
                }.replace('1', '#').replace('0', '.')
        }
    println("Part 1: ${part1(grid)}")
    println("Part 2: ${part2(grid)}")
}

fun part1(grid: List<String>): Int =
    grid.sumOf {
        it.count { c -> c == '#' }
    }

fun reachableBy(coord: Pair<Int, Int>, positions: List<Pair<Int, Int>>, visited: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    if (coord !in positions) return setOf()
    if (coord in visited) return visited

    val newVisited = visited.toMutableSet()
    newVisited.add(coord)

    val neighbours = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
    for ((dx, dy) in neighbours) {
        newVisited.addAll(reachableBy(coord.first + dx to coord.second + dy, positions, newVisited))
    }

    return newVisited
}

fun part2(grid: List<String>): Int {
    val squares = grid.withIndex().flatMap { (y, row) ->
        row.withIndex()
            .filter { (x, c) -> c == '#' }
            .map { (x, c) -> Pair(x, y) }
    }.toMutableList()

    var groupCount = 0
    while (squares.isNotEmpty()) {
        squares.removeAll(reachableBy(squares[0], squares, setOf()))
        groupCount++
    }

    return groupCount
}