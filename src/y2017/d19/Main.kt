package y2017.d19

import lib.readInput
import kotlin.math.min

fun main() {
    val input = readInput(2017, 19).lines()

    val (sol1 , sol2) = solve(input)
    println("Part 1: $sol1")
    println("Part 2: $sol2")
}

enum class Orientation {
    VERTICAL {
        override fun checkDirections(x: Int, y: Int, map: List<String>): Pair<String, String> {
            val line = map.map { it.getOrElse(x) { ' ' } }.joinToString("")
            return line.substring(0, y).reversed() to line.substring(y + 1)
        }

        override fun switch(): Orientation = HORIZONTAL
    },
    HORIZONTAL {
        override fun checkDirections(x: Int, y: Int, map: List<String>): Pair<String, String> =
            map[y].substring(0, x).reversed() to map[y].substring(x + 1)

        override fun switch(): Orientation = VERTICAL
    };

    abstract fun checkDirections(x: Int, y: Int, map: List<String>): Pair<String, String>
    abstract fun switch(): Orientation
}


fun solve(input: List<String>): Pair<String, Int> {
    var y = 0
    var x = input[0].indexOf('|')
    var orientation = Orientation.VERTICAL
    var result = ""
    var steps = 1

    while(true) {
        val (upOrLeft, downOrRight) = orientation.checkDirections(x, y, input)
        var (multiplier, line) = if (upOrLeft.isNotEmpty() && upOrLeft[0] != ' ') {
            -1 to upOrLeft
        } else {
            1 to downOrRight
        }
        val stopIndex = if (" " in line) min(line.indexOf('+'), line.indexOf(' ') - 1) else line.indexOf('+')
        line = line.substring(0, if (stopIndex != -1) stopIndex + 1 else line.length)
        result += line.filter { it.toString().matches(Regex("[A-Z]")) }
        println(result)

        when (orientation) {
            Orientation.VERTICAL -> y += multiplier * line.length
            Orientation.HORIZONTAL -> x += multiplier * line.length
        }
        steps += line.length
        if (line.last() != '+') break

        orientation = orientation.switch()
    }

    return result to steps
}
