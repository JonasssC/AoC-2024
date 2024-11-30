package y2017.d06

import lib.readStr

fun main() {
    val input = readStr(2017, 6)
        .split("\\s".toRegex())
        .map { it.toInt() }
    val (sol1, sol2) = solve(input)
    println("Part 1: $sol1")
    println("Part 2: $sol2")
}

fun solve(input: List<Int>): Pair<Int, Int> {
    val banks = input.toMutableList()
    val history = mutableListOf<List<Int>>()

    while (banks !in history) {
        history.add(banks.toList())
        val index = banks.indexOf(banks.max())
        val blocks = banks[index]
        banks[index] = 0
        for (i in 1..blocks) {
            banks[(index + i) % banks.size]++
        }
    }

    return Pair(history.size, history.size - history.indexOf(banks))
}
