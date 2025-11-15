package me.goldentrio

import me.task.Task
import me.task.source.standard.directory
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() = Task({
//    file("CCC2025Autumn/in/lvl2/level2_0_example.in") {
//        expected("CCC2025Autumn/in/lvl2/level2_0_example.out")
//    }
    directory("CCC2025Autumn/in/lvl2") {
        expected("level2_example.in", "level2_example.out")
    }
}) {
    readInt(endOfLine = true)

    while (hasNextLine()) {
        val paces = readInts()

        writeValue(
            paces.sumOf { it.sign },
            paces.sumOf {
                if (it == 0) 1
                else it.absoluteValue
            },
            lineBreak = true
        )
    }
}