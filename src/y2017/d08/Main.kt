package y2017.d08

import lib.readInput

enum class Operation(val key: String) {
    INCREMENT("inc") {
        override fun apply(a: Int, b: Int): Int = a + b
    },
    DECREMENT("dec") {
        override fun apply(a: Int, b: Int): Int = a - b
    };
    companion object {
        fun identify(key: String): Operation = entries.find { it.key == key }!!
    }

    abstract fun apply(a: Int, b: Int): Int
}

enum class Condition(val sign: String) {
    GREATER(">") {
        override fun passes(a: Int, b: Int): Boolean = a > b
    },
    LESS("<") {
        override fun passes(a: Int, b: Int): Boolean = a < b
    },
    GREATER_OR_EQUALS(">=") {
        override fun passes(a: Int, b: Int): Boolean = a >= b
    },
    LESS_OR_EQUALS("<=") {
        override fun passes(a: Int, b: Int): Boolean = a <= b
    },
    EQUALS("==") {
        override fun passes(a: Int, b: Int): Boolean = a == b
    },
    NOT_EQUALS("!=") {
        override fun passes(a: Int, b: Int): Boolean = a != b
    };
    companion object {
        fun identify(sign: String): Condition = Condition.entries.find { it.sign == sign }!!
    }

    abstract fun passes(a: Int, b: Int): Boolean
}

data class Instruction(val name: String, val op: Operation, val value: Int, val condition: Condition, val leftName: String, val rightValue: Int) {
    companion object {
        fun parse(line: String): Instruction {
            val split = line.split(" if ", " ")
            return Instruction(split[0], Operation.identify(split[1]), split[2].toInt(), Condition.identify(split[4]), split[3], split[5].toInt())
        }
    }

    fun perform(registers: MutableMap<String, Int>) {
        if (condition.passes(registers.getValue(leftName), rightValue)) {
            registers[name] = op.apply(registers.getValue(name), value)
        }
    }
}

fun main() {
    val input = readInput(2017, 8)
        .lines()
        .map { Instruction.parse(it) }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<Instruction>): Int {
    val registers = mutableMapOf<String, Int>().withDefault { 0 }
    input.forEach { it.perform(registers) }
    return registers.values.max()
}

fun part2(input: List<Instruction>): Int {
    val registers = mutableMapOf<String, Int>().withDefault { 0 }
    var max = 0
    input.forEach {
        it.perform(registers)
        val curMax = registers.values.max()
        max = maxOf(max, curMax)
    }
    return max
}
