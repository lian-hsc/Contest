package me.goldentrio

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
//    file("CCC2025Autumn/in/lvl4/level4_0_example.in") {
//        expected("CCC2025Autumn/in/lvl4/level4_0_example.out")
//    }
    directory("CCC2025Autumn/in/lvl4") {
        expected("level4_0_example.in", "level4_0_example.out")
    }
}){
    readInt(endOfLine = true)

    while (hasNextLine()) {
        val (position, _) = readValues()

        val (x, y) = position.split(",").map { it.toInt() }

        writeValue(generatePaces(x), lineBreak = true)
        writeValue(generatePaces(y), lineBreak = true)
        writeValue(lineBreak = true)
    }

}