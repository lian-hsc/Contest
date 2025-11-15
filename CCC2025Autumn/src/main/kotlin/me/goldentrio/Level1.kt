package me.goldentrio

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
//    file("CCC2025Autumn/in/lvl1/level1_example.in") {
//        expected("CCC2025Autumn/in/lvl1/level1_example.out")
//    }
    directory("CCC2025Autumn/in/lvl1") {
        expected("level1_example.in", "level1_example.out")
    }
}){
    readInt(endOfLine = true)

    while (hasNextLine()) {
        writeValue(readInts().sum(), lineBreak = true)
    }
}