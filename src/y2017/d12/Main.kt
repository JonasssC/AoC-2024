package y2017.d12

import lib.readStr

fun main() {
    val input = readStr(2017, 12).lines().associate {
        val split = it.split(" <-> ", ", ")
        Pair(split[0].toInt(), split.subList(1, split.size).map { x -> x.toInt() })
    }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun reachableBy(map: Map<Int, List<Int>>, current: Int, alreadyVisited: MutableSet<Int>): Set<Int> {
    if (current in alreadyVisited)
        return alreadyVisited

    alreadyVisited.add(current)

    for (connection in map.getValue(current)) {
        alreadyVisited.addAll(reachableBy(map, connection, alreadyVisited))
    }

    return alreadyVisited
}

fun part1(input: Map<Int, List<Int>>): Int {
    return reachableBy(input, 0, mutableSetOf()).size
}

fun part2(input: Map<Int, List<Int>>): Int {
    val notVisited = input.keys.toMutableList()
    var groupCount = 0
    while (notVisited.isNotEmpty()) {
        notVisited.removeAll(reachableBy(input, notVisited[0], mutableSetOf()))
        groupCount++
    }
    return groupCount
}