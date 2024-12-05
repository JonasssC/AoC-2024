package y2017.d18

import lib.readInput
import java.util.LinkedList
import java.util.Queue

fun main() {
    val input = readInput(2017, 18).lines().map { it.split(' ') }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun getValue(identifier: String, registers: Map<String, Long>): Long {
    if (identifier.matches(Regex("-?[0-9]+")))
        return identifier.toLong()
    return registers.getValue(identifier)
}

fun part1(input: List<List<String>>): Long {
    val registers = mutableMapOf<String, Long>().withDefault { 0 }
    var index = 0
    var lastSoundFrequency = -1L
    while (index < input.size) {
        val instruction = input[index]
        when (instruction[0]) {
            "snd" -> lastSoundFrequency = getValue(instruction[1], registers)
            "set" -> registers[instruction[1]] = getValue(instruction[2], registers)
            "add" -> registers.compute(instruction[1]) { _, v -> (v?:0) + getValue(instruction[2], registers) }
            "mul" -> registers.compute(instruction[1]) { _, v -> (v?:0) * getValue(instruction[2], registers) }
            "mod" -> registers.compute(instruction[1]) { _, v -> (v?:0) % getValue(instruction[2], registers) }
            "rcv" -> {
                if (getValue(instruction[1], registers) != 0L)
                    return lastSoundFrequency
            }
            "jgz" -> {
                if (getValue(instruction[1], registers) > 0) {
                    index += getValue(instruction[2], registers).toInt()
                    continue
                }
            }
        }
        index++
    }

    return -1
}

class Program(id: Long, private val instructions: List<List<String>>) {
    private val registers: MutableMap<String, Long> = mutableMapOf()
    private val queue: Queue<Long> = LinkedList()
    private var index = 0
    private var sendCount = 0

    init {
        registers["p"] = id
    }

    private fun hasTerminated() = index >= instructions.size

    fun isPossiblyInDeadlock() = hasTerminated() || (queue.isEmpty() && instructions[index][0] == "rcv")

    fun getSendCount() = sendCount

    private fun send(value: Long) = queue.offer(value)

    fun step(otherProgram: Program) {
        if (hasTerminated()) return
        val instruction = instructions[index]
        when (instruction[0]) {
            "snd" -> {
                sendCount++
                otherProgram.send(getValue(instruction[1], registers))
            }
            "set" -> registers[instruction[1]] = getValue(instruction[2], registers)
            "add" -> registers.compute(instruction[1]) { _, v -> (v?:0) + getValue(instruction[2], registers) }
            "mul" -> registers.compute(instruction[1]) { _, v -> (v?:0) * getValue(instruction[2], registers) }
            "mod" -> registers.compute(instruction[1]) { _, v -> (v?:0) % getValue(instruction[2], registers) }
            "rcv" -> {
                if (queue.isEmpty()) return
                registers[instruction[1]] = queue.poll()
            }
            "jgz" -> {
                if (getValue(instruction[1], registers) > 0) {
                    index += getValue(instruction[2], registers).toInt()
                    return
                }
            }
        }
        index++
    }
}

fun part2(input: List<List<String>>): Int {
    val program0 = Program(0, input)
    val program1 = Program(1, input)

    while (!(program0.isPossiblyInDeadlock() && program1.isPossiblyInDeadlock())) {
        program0.step(program1)
        program1.step(program0)
    }

    return program1.getSendCount()
}
