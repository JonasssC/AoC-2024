package y2024.d04

import lib.readStr

typealias Input = List<String>
typealias Output = Int

fun main() {
    val input = readStr(2024, 4).lines()

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

val directions = listOf(
    -1 to -1,
    -1 to 0,
    -1 to 1,
    0 to -1,
    0 to 1,
    1 to -1,
    1 to 0,
    1 to 1,
)

fun inField(input: Input, row: Int, col: Int) =
    0 <= row && row < input.size && 0 <= col && col < input[row].length

fun part1(input: Input): Output =
    input.flatMapIndexed { row, line ->
        line.withIndex().filter { it.value == 'X' }.map { row to it.index }
    }.sumOf { (row, col) ->
        directions.count { (dRow, dCol) ->
            inField(input, row + 3 * dRow, col + 3 * dCol)
                    && input[row + dRow][col + dCol] == 'M'
                    && input[row + 2 * dRow][col + 2 * dCol] == 'A'
                    && input[row + 3 * dRow][col + 3 * dCol] == 'S'
        }
    }

fun part2(input: Input): Output =
    input.flatMapIndexed { row, line ->
        line.withIndex().filter { it.value == 'A' }.map { row to it.index }
    }.count { (row, col) ->
        inField(input, row - 1, col - 1)
                && inField(input, row + 1, col + 1)
                && listOf(input[row + 1][col + 1], input[row - 1][col - 1]).sorted() == listOf('M', 'S')
                && listOf(input[row + 1][col - 1], input[row - 1][col + 1]).sorted() == listOf('M', 'S')
    }