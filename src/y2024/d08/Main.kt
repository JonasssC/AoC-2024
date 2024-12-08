package y2024.d08

import lib.readInput

typealias Output = Int
typealias Input = List<String>

fun main() {
    val input = readInput(2024, 8).lines()
    val dim = input.size to input[0].length
    val antennas = input.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, c -> if (c != '.') (row to col) to c else null }
    }

    println("Part 1: ${part1(dim, antennas)}")
    println("Part 2: ${part2(dim, antennas)}")
}

fun inField(dim: Pair<Int, Int>, point: Pair<Int, Int>): Boolean =
    0 <= point.first && point.first < dim.first && 0 <= point.second && point.second < dim.second

fun part1(dim: Pair<Int, Int>, antennas: List<Pair<Pair<Int, Int>, Char>>): Output =
    antennas.flatMap { antenna ->
        antennas.filter { otherAntenna ->
            antenna.first != otherAntenna.first && antenna.second == otherAntenna.second
        }.map { otherAntenna ->
            2 * antenna.first.first - otherAntenna.first.first to 2 * antenna.first.second - otherAntenna.first.second
        }.filter {
            inField(dim, it)
        }
    }.distinct().size

fun part2(dim: Pair<Int, Int>, antennas: List<Pair<Pair<Int, Int>, Char>>): Output =
    antennas.flatMap { antenna ->
        antennas.filter { otherAntenna ->
            antenna.first != otherAntenna.first && antenna.second == otherAntenna.second
        }.flatMap { otherAntenna ->
            val antiNodes = mutableSetOf<Pair<Int, Int>>()
            val dX = antenna.first.first - otherAntenna.first.first
            val dY = antenna.first.second - otherAntenna.first.second
            var i = 0
            while (inField(dim , antenna.first.first + i * dX to antenna.first.second + i * dY)) {
                antiNodes.add(antenna.first.first + i * dX to antenna.first.second + i * dY)
                i++
            }
            antiNodes
        }.filter {
            inField(dim, it)
        }
    }.distinct().size
