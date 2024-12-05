package y2017.d09

import lib.readInput

fun main() {
    val input = readInput(2017, 9)
    val (sol1, sol2) = solve(input)
    println("Part 1: $sol1")
    println("Part 2: $sol2")
}

fun solve(input: String): Pair<Int, Int> {
    var score = 0
    var garbageCount = 0
    var depth = 0
    var inGarbage = false
    var skipNext = false
    for (c in input) {
        if (skipNext)
            skipNext = false
        else if (inGarbage) {
            when (c) {
                '>' -> inGarbage = false
                '!' -> skipNext = true
                else -> garbageCount++
            }
        } else {
            when (c) {
                '<' -> inGarbage = true
                '{' -> depth++
                '}' -> {
                    score += depth
                    depth--
                }
            }
        }
    }

    return Pair(score, garbageCount)
}