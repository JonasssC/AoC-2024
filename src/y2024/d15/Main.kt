package y2024.d15

import lib.readInput
import lib.plus

typealias Output = Int
typealias Input = List<String>
typealias Point = Pair<Int, Int>

fun main() {
    val (map, movements) = readInput(2024, 15).split("\n\n").map { it.lines() }
    val robot = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == '@') row to col else null } }.first()
    val boxes = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == 'O') row to col else null } }
    val walls = map.flatMapIndexed { row, line -> line.mapIndexedNotNull { col, char -> if (char == '#') row to col else null } }
    println("Part 1: ${part1(robot, boxes, walls, movements)}")
    println("Part 2: ${part2(robot, boxes, walls, movements)}")
}

fun move(movingItem: Point, boxes: MutableList<Point>, walls: List<Point>, direction: Point): Point {
    val moved = movingItem + direction
    if (moved in walls) {
        return movingItem
    }
    if (moved in boxes) {
        val movedBox = move(moved, boxes, walls, direction)
        if (movedBox == moved) {
            return movingItem
        }
        boxes.replaceAll { if (it == moved) movedBox else it }
    }
    return moved
}

fun part1(robot: Point, boxes: List<Point>, walls: List<Point>, movements: Input): Output {
    var movingRobot = robot
    val movingBoxes = boxes.toMutableList()
    for (movement in movements.joinToString("")) {
        movingRobot = when(movement) {
            '>' -> move(movingRobot, movingBoxes, walls, 0 to 1)
            '<' -> move(movingRobot, movingBoxes, walls, 0 to -1)
            '^' -> move(movingRobot, movingBoxes, walls, -1 to 0)
            'v' -> move(movingRobot, movingBoxes, walls, 1 to 0)
            else -> movingRobot
        }
    }
    return movingBoxes.sumOf { 100 * it.first + it.second }
}

fun move2(movingItem: Point, boxes: MutableList<Pair<Point, Point>>, walls: List<Point>, direction: Point): Point {
    val moved = movingItem + direction
    if (moved in walls) {
        return movingItem
    }
    val hitBox = boxes.find { it.second == moved || it.first == moved }
    if (hitBox != null) {
        val movingBoxes = collectBoxes(hitBox, boxes, direction)
        if (movingBoxes.any { it.first + direction in walls || it.second + direction in walls }) {
            return movingItem
        } else {
            boxes.replaceAll { if (it in movingBoxes) it.first + direction to it.second + direction else it }
            return moved
        }
    }
    return moved
}

fun collectBoxes(movingBox: Pair<Point, Point>, boxes: List<Pair<Point, Point>>, direction: Point): Set<Pair<Point, Point>> {
    val hitBoxes = if (direction.first == 0) {
        val check = when (direction.second) {
            1 -> movingBox.second.first to movingBox.second.second + 1
            -1 -> movingBox.first.first to movingBox.first.second - 1
            else -> movingBox.second
        }
        boxes.filter { it.first == check || it.second == check }
    } else {
        val checks = listOf(movingBox.first + direction, movingBox.second + direction)
        boxes.filter { it.first in checks || it.second in checks }
    }
    val collected = hitBoxes.toMutableSet()
    collected.add(movingBox)
    hitBoxes.forEach {
        collected.addAll(collectBoxes(it, boxes, direction))
    }
    return collected
}

fun part2(robot: Point, boxes: List<Point>, walls: List<Point>, movements: Input): Output {
    var movingRobot = robot.first to robot.second * 2
    val movingBoxes = boxes.map { (it.first to it.second * 2) to (it.first to it.second * 2 + 1) }.toMutableList()
    val doubleWalls = walls.flatMap { listOf(it.first to it.second * 2, it.first to it.second * 2 + 1) }
    for (movement in movements.joinToString("")) {
        movingRobot = when(movement) {
            '>' -> move2(movingRobot, movingBoxes, doubleWalls, 0 to 1)
            '<' -> move2(movingRobot, movingBoxes, doubleWalls, 0 to -1)
            '^' -> move2(movingRobot, movingBoxes, doubleWalls, -1 to 0)
            'v' -> move2(movingRobot, movingBoxes, doubleWalls, 1 to 0)
            else -> movingRobot
        }
    }
    return movingBoxes.sumOf { 100 * it.first.first + it.first.second }
}
