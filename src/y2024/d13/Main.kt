package y2024.d13

import lib.readInput

typealias Output = Long
typealias Input = List<ClawMachine>

class ClawMachine(val dXA: Long, val dYA: Long, val dXB: Long, val dYB: Long, val x: Long, val y: Long) {
    companion object {
        fun parse(input: String): ClawMachine {
            val matches =
                Regex("Button A: X\\+(\\d+), Y\\+(\\d+)\\nButton B: X\\+(\\d+), Y\\+(\\d+)\\nPrize: X=(\\d+), Y=(\\d+)").find(
                    input
                )!!.groupValues
            return ClawMachine(
                matches[1].toLong(),
                matches[2].toLong(),
                matches[3].toLong(),
                matches[4].toLong(),
                matches[5].toLong(),
                matches[6].toLong()
            )
        }
    }
}

fun main() {
    val input = readInput(2024, 13).split("\n\n").map { ClawMachine.parse(it) }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Input): Output =
    input.sumOf {
        if ((it.dXB * it.y - it.dYB * it.x) % (it.dYA * it.dXB - it.dXA * it.dYB) != 0L) return@sumOf 0
        val a = (it.dXB * it.y - it.dYB * it.x) / (it.dYA * it.dXB - it.dXA * it.dYB)
        if ((it.x - a * it.dXA) % it.dXB != 0L) return@sumOf 0
        val b = (it.x - a * it.dXA) / it.dXB
        3 * a + b
    }

fun part2(input: Input): Output =
    input.sumOf {
        val x = 10000000000000 + it.x
        val y = 10000000000000 + it.y
        if ((it.dXB * y - it.dYB * x) % (it.dYA * it.dXB - it.dXA * it.dYB) != 0L) return@sumOf 0
        val a = (it.dXB * y - it.dYB * x) / (it.dYA * it.dXB - it.dXA * it.dYB)
        if ((x - a * it.dXA) % it.dXB != 0L) return@sumOf 0
        val b = (x - a * it.dXA) / it.dXB
        3 * a + b
    }