package y2017.d17

fun main() {
    val input = 324

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Int): Int {
    val buffer = mutableListOf(0)
    var index = 0

    for (i in 1..2017) {
        index += input
        index %= buffer.size
        index += 1
        buffer.add(index, i)
    }

    return buffer[(index + 1) % buffer.size]
}

fun part2(input: Int): Int {
    var size = 1
    var index = 0
    var last = 0

    for (i in 1..50000000) {
        index += input
        index %= size
        index += 1
        if (index == 1)
            last = i
        size++
    }

    return last
}
