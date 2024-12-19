package y2024.d19

import lib.readInput

typealias Output = Int
typealias Input = List<String>

fun main() {
    val input = readInput(2024, 19).split("\n\n")
    val patterns = input[0].split(", ")
    val designs = input[1].lines()

    println("Part 1: ${part1(patterns, designs)}")
    println("Part 2: ${part2(patterns, designs)}")
}

fun canMakeDesign(design: String, patterns: List<String>): Boolean {
    if (design == "")
        return true

    for (pattern in patterns)
        if (design.startsWith(pattern))
            if (canMakeDesign(design.substring(pattern.length), patterns))
                return true

    return false
}

fun part1(patterns: Input, designs: Input): Output =
    designs.count {
        canMakeDesign(it, patterns)
    }

val countPossibilitiesMem = mutableMapOf<Pair<String, List<String>>, Long>()
fun countPossibilities(design: String, patterns: List<String>): Long {
    if (design to patterns in countPossibilitiesMem)
        return countPossibilitiesMem[design to patterns]!!

    if (design == "")
        return 1

    var count = 0L
    for (pattern in patterns)
        if (design.startsWith(pattern))
            count += countPossibilities(design.substring(pattern.length), patterns)

    countPossibilitiesMem[design to patterns] = count
    return count
}

fun part2(patterns: Input, designs: Input): Long =
    designs.sumOf {
        countPossibilities(it, patterns)
    }
