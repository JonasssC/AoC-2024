package y2017.d07

import lib.readStr

data class Program(val name: String, val weight: Int, val children: List<String>) {
    companion object {
        fun parse(line: String): Program {
            val split = line.split(" (", ") -> ", ")", ", ").filter(String::isNotEmpty)
            val (name, weightString) = split
            return Program(name, weightString.toInt(), split.subList(2, split.size))
        }
    }

    fun getFullWeight(programs: Map<String, Program>): Int
        = weight + children.sumOf {
            programs.getValue(it).getFullWeight(programs)
        }
}

fun main() {
    val input = readStr(2017, 7)
        .lines()
        .map { Program.parse(it) }
        .groupBy { it.name }
        .mapValues { it.value.first() }
    val root = part1(input)
    println("Part 1: $root")
    println("Part 2: ${part2(input, root)}")
}

fun part1(input: Map<String, Program>): String =
    input.keys.first {
        input.values.none { other ->
            it in other.children
        }
    }

fun part2(input: Map<String, Program>, root: String): Int {
    val program = input.getValue(root)

    val childWeights = program.children.map { input.getValue(it).getFullWeight(input) }
    val areChildrenBalanced = childWeights.distinct().size == 1
    if (areChildrenBalanced) return -1

    if (childWeights.size == 2) {
        val res1 = part2(input, program.children[0])
        if (res1 != -1) return res1
        return part2(input, program.children[1])
    }

    val difIndex = childWeights.indexOfFirst { childWeights.filter { other -> it == other}.size == 1 }

    val res = part2(input, program.children[difIndex])
    if (res != -1) return res

    val wantedWeight = childWeights.first { childWeights.filter { other -> it == other}.size > 1 }

    val wrongChild = input.getValue(program.children[difIndex])
    val dif = wantedWeight - wrongChild.getFullWeight(input)
    return wrongChild.weight + dif
}
