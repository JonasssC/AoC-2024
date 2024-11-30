package y2017.d10

import lib.readStr

fun main() {
    val input = readStr(2017, 10)
    println("Part 1: ${part1(input.split(",").map { it.toInt() })}")
    println("Part 2: ${part2(input)}")
}

fun reverseSectionInPlace(list: MutableList<Int>, leftBound: Int, rightBound: Int) {
    var left = leftBound
    var right = rightBound
    while (left < right) {
        val temp = list[left % list.size]
        list[left % list.size] = list[right % list.size]
        list[right % list.size] = temp
        left++
        right--
    }
}

fun part1(input: List<Int>): Int {
    val list = (0..255).toMutableList()
    var index = 0

    for ((skipSize, length) in input.withIndex()) {
        reverseSectionInPlace(list, index, index + length - 1)
        index += length + skipSize
        index %= list.size
    }

    return list[0] * list[1]
}

fun knotHash(input: String): String {
    val seq = input.map { it.code }.toMutableList()
    seq.addAll(listOf(17, 31, 73, 47, 23))

    val list = (0..255).toMutableList()
    var index = 0
    var skipSize = 0

    for (i in 0..<64) {
        for (length in seq) {
            reverseSectionInPlace(list, index, index + length - 1)
            index += length + skipSize
            index %= list.size
            skipSize++
        }
    }

    return list.chunked(16).joinToString("") { Integer.toHexString(it.reduce { acc, i -> acc xor i }).padStart(2, '0') }
}

fun part2(input: String): String = knotHash(input)