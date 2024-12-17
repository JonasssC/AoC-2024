package y2024.d17

import lib.readInput
import kotlin.collections.set

typealias Output = String
typealias Input = List<String>

fun main() {
    val input = readInput(2024, 17).split("\n\n")

    val registers = input[0].lines().associate {
        val match = Regex("Register ([ABC]): (\\d+)").matchEntire(it)!!
        match.groupValues[1] to match.groupValues[2].toLong()
    }

    val program = input[1].substring(9).split(",").map(String::toInt)

    println("Part 1: ${part1(registers, program)}")
    println("Part 2: ${part2(program)}")
}

fun getComboOperand(registers: Map<String, Long>, operand: Int): Long =
    when (operand) {
        4 -> registers["A"]
        5 -> registers["B"]
        6 -> registers["C"]
        else -> operand.toLong()
    }!!

fun part1(registers: Map<String, Long>, program: List<Int>): Output {
    val mutableRegisters = registers.toMutableMap()
    var instructionPointer = 0
    val output = mutableListOf<Int>()

    while (instructionPointer < program.size - 1) {
        val opcode = program[instructionPointer]
        val operand = program[instructionPointer + 1]

        when (opcode) {
            0 -> mutableRegisters["A"] = mutableRegisters["A"]!! shr getComboOperand(mutableRegisters, operand).toInt()
            1 -> mutableRegisters["B"] = mutableRegisters["B"]!! xor operand.toLong()
            2 -> mutableRegisters["B"] = getComboOperand(mutableRegisters, operand) and 7
            3 -> {
                if (mutableRegisters["A"] != 0L) {
                    instructionPointer = operand
                    continue
                }
            }
            4 -> mutableRegisters["B"] = mutableRegisters["B"]!! xor mutableRegisters["C"]!!
            5 -> output.add((getComboOperand(mutableRegisters, operand) and 7L).toInt())
            6 -> mutableRegisters["B"] = mutableRegisters["A"]!! shr getComboOperand(mutableRegisters, operand).toInt()
            7 -> mutableRegisters["C"] = mutableRegisters["A"]!! shr getComboOperand(mutableRegisters, operand).toInt()
        }
        instructionPointer += 2
    }

    return output.joinToString(",")
}

fun part2(program: List<Int>): Long {
    var options = listOf(0L)
    val remainingProgram = program.toMutableList()
    val expectedOutput = mutableListOf<Int>()
    while (remainingProgram.isNotEmpty()) {
        expectedOutput.addFirst(remainingProgram.removeLast())
        options = options.flatMap {
            val subOptions = mutableListOf<Long>()
            for (i in 0..7) {
                val attempt = (it shl 3) or i.toLong()
                val res = part1(mapOf("A" to attempt, "B" to 0L, "C" to 0L), program)
                if (res == expectedOutput.joinToString(",")) {
                    subOptions.add(attempt)
                }
            }
            subOptions
        }
    }

    return options.min()
}
