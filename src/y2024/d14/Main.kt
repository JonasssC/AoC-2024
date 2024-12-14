package y2024.d14

import lib.readInput

typealias Output = Int
typealias Input = List<Robot>

const val WIDTH = 101
const val HEIGHT = 103
//const val WIDTH = 11
//const val HEIGHT = 7
const val DEBUG = false

class Robot(val x: Int, val y: Int, val vx: Int, val vy: Int) {
    fun moveTimes(times: Int): Robot {
        val newX = ((x + times * vx) % WIDTH + 2 * WIDTH) % WIDTH
        val newY = ((y + times * vy) % HEIGHT + 2 * HEIGHT) % HEIGHT
        return Robot(newX, newY, vx, vy)
    }

    companion object {
        fun parse(str: String): Robot {
            val nums = str.split("p=", ",", " v=")
            return Robot(nums[1].toInt(), nums[2].toInt(), nums[3].toInt(), nums[4].toInt())
        }
    }
}

fun main() {
    val input = readInput(2024, 14).lines().map { Robot.parse(it) }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Input): Output {
    val movedRobots = input.map {
        it.moveTimes(100)
    }

    val upperLeft = movedRobots.count { it.x < WIDTH / 2 && it.y < HEIGHT / 2 }
    val upperRight = movedRobots.count { it.x > WIDTH / 2 && it.y < HEIGHT / 2 }
    val lowerLeft = movedRobots.count { it.x < WIDTH / 2 && it.y > HEIGHT / 2 }
    val lowerRight = movedRobots.count { it.x > WIDTH / 2 && it.y > HEIGHT / 2 }
    return upperLeft * upperRight * lowerLeft * lowerRight
}

fun printMap(robots: List<Robot>) {
    for (row in 0..<WIDTH) {
        for (col in 0..<HEIGHT) {
            if (robots.any { it.x == col && it.y == row }) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

fun part2(input: Input): Output {
    var robots = input
    var i = 0
    while (robots.size != robots.distinctBy { it.x to it.y }.size) {
        robots = robots.map { it.moveTimes(1) }
        i++
    }
    if (DEBUG) {
        printMap(robots)
    }
    return i
}