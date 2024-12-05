package y2017.d11

import lib.readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput(2017, 11).split(",")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun reduce(amountNPar: Int, amountNePar: Int, amountNwPar: Int): Triple<Int, Int, Int> {
    var amountN = amountNPar
    var amountNe = amountNePar
    var amountNw = amountNwPar
    while (true) {
        when {
            amountNw > 0 && amountNe > 0 -> {
                val dif = min(amountNw, amountNe)
                amountN += dif
                amountNw -= dif
                amountNe -= dif
            }
            amountNw < 0 && amountNe < 0 -> {
                val dif = min(abs(amountNw), abs(amountNe))
                amountN -= dif
                amountNw += dif
                amountNe += dif
            }
            amountN > 0 && amountNw < 0 -> {
                val dif = min(amountN, abs(amountNw))
                amountNe += dif
                amountN -= dif
                amountNw += dif
            }
            amountN < 0 && amountNw > 0 -> {
                val dif = min(abs(amountN), amountNw)
                amountNe -= dif
                amountN += dif
                amountNw -= dif
            }
            amountNe < 0 && amountN > 0 -> {
                val dif = min(abs(amountNe), amountN)
                amountNw += dif
                amountN -= dif
                amountNe += dif
            }
            amountNe > 0 && amountN < 0 -> {
                val dif = min(amountNe, abs(amountN))
                amountNw -= dif
                amountN += dif
                amountNe -= dif
            }
            else -> return Triple(amountN, amountNe, amountNw)
        }
    }

}

fun part1(input: List<String>): Int {
    var amountN = input.count { it == "n" } - input.count { it == "s" }
    var amountNe = input.count { it == "ne" } - input.count { it == "sw" }
    var amountNw = input.count { it == "nw" } - input.count { it == "se" }

    val res = reduce(amountN, amountNe, amountNw)
    amountN = res.first
    amountNe = res.second
    amountNw = res.third

    println("$amountNw, $amountN, $amountNe")

    return abs(amountNw) + abs(amountN) + abs(amountNe)
}

fun part2(input: List<String>): Int {
    var amountN = 0
    var amountNe = 0
    var amountNw = 0
    var maxDistance = 0

    input.forEach {
        when (it) {
            "n" -> amountN++
            "s" -> amountN--
            "ne" -> amountNe++
            "sw" -> amountNe--
            "nw" -> amountNw++
            "se" -> amountNw--
        }

        val res = reduce(amountN, amountNe, amountNw)
        amountN = res.first
        amountNe = res.second
        amountNw = res.third

        maxDistance = max(maxDistance, abs(amountNw) + abs(amountN) + abs(amountNe))
    }

    return maxDistance
}