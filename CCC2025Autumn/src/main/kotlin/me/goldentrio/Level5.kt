package me.goldentrio

import me.task.Task
import me.task.source.standard.directory
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.max

fun main() = Task({
//    file("CCC2025Autumn/in/lvl5/level5_0_example.in") {
//        expected("CCC2025Autumn/in/lvl5/level5_0_example.out")
//    }
    directory("CCC2025Autumn/in/lvl5") {
        expected("level4_0_example.in", "level5_0_example.out")
    }
}) {
    readInt(endOfLine = true)

    while (hasNextLine()) {
        val goal = readValues().first().toXY()
        val astroidPosition = readValues().first().toXY()

        val path = aStar(goal) { abs(it.x - astroidPosition.x) <= 2 && abs(it.y - astroidPosition.y) <= 2 }
        val paths = listOf(
            listOf(Coordinates()),
            findTurns(path),
            listOf(goal)
        ).flatten()
            .zipWithNext()
            .map { generateStraightPaces(it.first, it.second) }
            .map {
                val time = max(it.first.overallTime(), it.second.overallTime())

                if (it.first.overallTime() == time) {
                    it.first to (it.second + (0..<(time - it.second.overallTime())).map { 0 })
                } else {
                    (it.first + (0..<(time - it.first.overallTime())).map { 0 }) to it.second
                }
            }
            .reduce { a, b -> a.first + b.first to a.second + b.second }

        writeValue(*paths.first.toTypedArray(), lineBreak = true)
        writeValue(*paths.second.toTypedArray(), lineBreak = true)
        writeValue(lineBreak = true)
    }

}

fun String.toXY(): Coordinates {
    val (x, y) = this.split(",").map { it.toInt() }
    return Coordinates(x, y)
}

fun aStar(
    goal: Coordinates,
    isBlocked: (Coordinates) -> Boolean
): List<Coordinates> {
    val start = Coordinates()

    data class Node(
        val point: Coordinates,
        val g: Int,           // actual cost from start
        val h: Int,           // heuristic to goal
        val f: Int,           // g + h
        val parent: Node?,
        val dir: Coordinates? // last movement direction
    )

    fun heuristic(a: Coordinates, b: Coordinates): Int =
        abs(a.x - b.x) + abs(a.y - b.y)

    val open = java.util.PriorityQueue<Node>(compareBy { it.f })
    val closed = HashSet<Coordinates>()

    val startNode = Node(start, 0, heuristic(start, goal),
        heuristic(start, goal), null, null)
    open.add(startNode)

    // 4-way movement
    val directions = listOf(
        Coordinates(1, 0),
        Coordinates(-1, 0),
        Coordinates(0, 1),
        Coordinates(0, -1)
    )

    val gScore = mutableMapOf<Coordinates, Int>()
    gScore[start] = 0

    while (open.isNotEmpty()) {
        val current = open.poll()

        if (current.point == goal) {
            val path = mutableListOf<Coordinates>()
            var node: Node? = current
            while (node != null) {
                path.add(node.point)
                node = node.parent
            }
            return path.reversed()
        }

        closed.add(current.point)

        for (d in directions) {
            val nextPoint = Coordinates(current.point.x + d.x, current.point.y + d.y)

            if (isBlocked(nextPoint) || nextPoint in closed) continue

            // TURN PENALTY — prefer straight lines
            val turnPenalty =
                if (current.dir != null && (current.dir.x != d.x || current.dir.y != d.y))
                    1     // a small cost for changing direction
                else
                    0

            val tentativeG = current.g + 1 + turnPenalty
            val existingG = gScore[nextPoint]

            if (existingG == null || tentativeG < existingG) {
                gScore[nextPoint] = tentativeG
                val h = heuristic(nextPoint, goal)
                open.add(
                    Node(
                        nextPoint,
                        tentativeG,
                        h,
                        tentativeG + h,
                        current,
                        d // store movement direction
                    )
                )
            }
        }
    }

    return emptyList() // No path found
}

fun findTurns(path: List<Coordinates>): List<Coordinates> {
    if (path.size < 3) return emptyList()

    val turns = mutableListOf<Coordinates>()

    for (i in 1 until path.size - 1) {
        val prev = path[i - 1]
        val curr = path[i]
        val next = path[i + 1]

        // direction vectors
        val dx1 = curr.x - prev.x
        val dy1 = curr.y - prev.y
        val dx2 = next.x - curr.x
        val dy2 = next.y - curr.y

        // if direction changes, it's a turn
        if (dx1 != dx2 || dy1 != dy2) {
            turns.add(curr)
        }
    }

    return turns
}


fun generateStraightPaces(start: Coordinates, end: Coordinates): Pair<List<Int>, List<Int>> =
    generatePaces(start.x, end.x) to generatePaces(start.y, end.y)

fun List<Int>.overallTime() = sumOf { if (it == 0) 1 else it.absoluteValue }