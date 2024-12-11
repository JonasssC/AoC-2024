package y2024.d09

import lib.readInput

typealias Output = Long
typealias Input = String

fun main() {
    val input = readInput(2024, 9)

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun toBlockRepresentation(diskMap: String): List<String> {
    var nextIsBlock = true
    var i = 0
    val blockRepresentation = mutableListOf<String>()
    for (c in diskMap) {
        if (nextIsBlock) {
            blockRepresentation += (0..<c.digitToInt()).map { "$i" }
            i++
        } else {
            blockRepresentation += (0..<c.digitToInt()).map { "." }
        }
        nextIsBlock = !nextIsBlock
    }
    return blockRepresentation.dropLastWhile { it == "." }
}

fun part1(input: Input): Output {
    var blockRepresentation = toBlockRepresentation(input).toMutableList()
    var i = 0
    while (i < blockRepresentation.size - 1) {
        if (blockRepresentation[i] == ".") {
            blockRepresentation[i] = blockRepresentation.removeLast()
            blockRepresentation = blockRepresentation.dropLastWhile { it == "." }.toMutableList()
        }
        i++
    }
    return blockRepresentation.mapIndexed { index, c -> index.toLong() * c.toInt().toLong() }.sum()
}

fun toTupleBlockRepresentation(diskMap: String): List<Pair<String, Int>> {
    var nextIsBlock = true
    var i = 0
    val blockRepresentation = mutableListOf<Pair<String, Int>>()
    for (c in diskMap) {
        if (nextIsBlock) {
            blockRepresentation += "$i" to c.digitToInt()
            i++
        } else {
            blockRepresentation += "." to c.digitToInt()
        }
        nextIsBlock = !nextIsBlock
    }
    return blockRepresentation
}

fun part2(input: Input): Output {
    val blockRepresentation = toTupleBlockRepresentation(input).toMutableList()
    var i = input.length / 2

    while (i > 0) {
        val cur = blockRepresentation.find { it.first == "$i" }!!
        val open = blockRepresentation.find { it.first == "." && it.second >= cur.second }
        if (open != null) {
            val indexCur = blockRepresentation.indexOf(cur)
            val indexOpen = blockRepresentation.indexOf(open)

            if (indexOpen < indexCur) {
                blockRepresentation[indexOpen] = cur
                blockRepresentation[indexCur] = "." to cur.second
                val remaining = open.second - cur.second
                if (remaining > 0) {
                    blockRepresentation.add(indexOpen + 1, "." to remaining)
                }
            }
        }
        i--
    }

    return blockRepresentation.flatMap {
        (0..<it.second).map { _ -> it.first }
    }.mapIndexed { index, s ->
        if (s == ".") 0L else index.toLong() * s.toInt().toLong()
    }.sum()
}
