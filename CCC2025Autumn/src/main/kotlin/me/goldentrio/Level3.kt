package me.goldentrio

import me.task.Task
import me.task.source.standard.directory
import kotlin.math.*

fun main() = Task({
//    file("CCC2025Autumn/in/lvl3/level3_0_example.in") {
//        expected("CCC2025Autumn/in/lvl3/level3_0_example.out")
//    }
    directory("CCC2025Autumn/in/lvl3") {
        expected("level3_example.in", "level3_example.out")
    }
}){
    readInt(endOfLine = true)

    while (hasNextLine()) {
        val (position, _) = readInts()
        writeValue(generatePaces(position), lineBreak = true)
    }

}

fun generatePaces(position: Int): List<Int> = buildList {
    add(0)
    var lastPace = if (position < 0) -5 else 5
    val factor = position.sign

    val steps = position.absoluteValue

    val upDownSteps = floor(min(steps, 8) / 2.0).toInt()
    val keepSteps = max(steps - 8, min(steps, 8) % 2)

    for (ignore in 0..<upDownSteps) {
        add(lastPace)
        lastPace -= factor
    }

    for (ignore in 0..<keepSteps) {
        add(lastPace)
    }

    for (ignore in 0..<upDownSteps) {
        lastPace += factor
        add(lastPace)
    }

    add(0)
}

fun generatePaces(from: Int, to: Int): List<Int> = buildList {
    add(0)
    var lastPace = if (to - from < 0) -5 else 5
    val factor = (to - from).sign

    val steps = (to - from).absoluteValue

    val upDownSteps = floor(min(steps, 8) / 2.0).toInt()
    val keepSteps = max(steps - 8, min(steps, 8) % 2)

    for (ignore in 0..<upDownSteps) {
        add(lastPace)
        lastPace -= factor
    }

    for (ignore in 0..<keepSteps) {
        add(lastPace)
    }

    for (ignore in 0..<upDownSteps) {
        lastPace += factor
        add(lastPace)
    }

    add(0)
}