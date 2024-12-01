package y2017.d15

const val multiplierA = 16807
const val multiplierB = 48271
const val mod = 2147483647
const val mask: Long = 0b1111111111111111
const val maskA: Long = 0b11
const val maskB: Long = 0b111

fun main() {
    val genA = 277
    val genB = 349

    println("Part 1: ${part1(genA, genB)}")
    println("Part 2: ${part2(genA, genB)}")
}

fun part1(genA: Int, genB: Int): Int {
    var a: Long = genA.toLong()
    var b: Long = genB.toLong()

    var count = 0
    for (i in 0..<40000000) {
        a *= multiplierA
        b *= multiplierB
        a %= mod
        b %= mod
        if (a and mask == b and mask)
            count++
    }

    return count
}

fun part2(genA: Int, genB: Int): Int {
    var a: Long = genA.toLong()
    var b: Long = genB.toLong()

    var count = 0
    var checked = 0
    while (checked < 5000000) {
        do {
            a *= multiplierA
            a %= mod
        } while (a and maskA != 0L)
        do {
            b *= multiplierB
            b %= mod
        } while (b and maskB != 0L)
        if (a and mask == b and mask)
            count++
        checked++
    }

    return count
}