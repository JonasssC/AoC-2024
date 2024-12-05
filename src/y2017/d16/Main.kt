package y2017.d16

import lib.readInput

abstract class Move {
    abstract fun perform(line: MutableList<Char>): MutableList<Char>

    companion object {
        fun parse(str: String): Move {
            if (str[0] == 's')
                return Spin(str.substring(1).toInt())
            if (str[0] == 'x') {
                val (pos1, pos2) = str.substring(1).split('/')
                return Exchange(pos1.toInt(), pos2.toInt())
            }
            val (_, program1, _, program2) = str.toCharArray()
            return Partner(program1, program2)
        }
    }
}

class Spin(private var stepCount: Int) : Move() {
    override fun perform(line: MutableList<Char>): MutableList<Char> {
        for (i in 0..<stepCount) line.addFirst(line.removeLast())
        return line
    }
}

class Exchange(private var pos1: Int, private var pos2: Int) : Move() {
    override fun perform(line: MutableList<Char>): MutableList<Char> {
        val temp = line[pos1]
        line[pos1] = line[pos2]
        line[pos2] = temp
        return line
    }
}

class Partner(private var program1: Char, private var program2: Char) : Move() {
    override fun perform(line: MutableList<Char>): MutableList<Char> {
        line.replaceAll {
            when (it) {
                program1 -> program2
                program2 -> program1
                else -> it
            }
        }
        return line
    }
}

fun main() {
    val input = readInput(2017, 16).split(',').map { Move.parse(it) }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<Move>): String =
    input.fold("abcdefghijklmnop".toMutableList()) { line, move ->
        move.perform(line)
    }.joinToString("")

fun part2(input: List<Move>): String {
    val line = "abcdefghijklmnop".toMutableList()
    val history = mutableListOf<List<Char>>()
    while (line !in history) {
        history.add(line.toList())
        input.forEach { it.perform(line) }
    }
    val index = history.indexOf(line)
    val dif = 1000000000 % (history.size - index)
    return history[index+dif].joinToString("")
}