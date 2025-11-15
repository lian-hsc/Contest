package me.task.util.extention

import kotlin.io.path.Path

/**
 * Gets a [Path] object from this string.
 */
val String.path
    get() = Path(this)

/**
 * Creates a random string with the given [length].
 */
fun String.Companion.random(length: Int = 8, source: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"): String {
    return (1..length)
        .map { source.random() }
        .joinToString("")
}

/**
 * Returns a string with only distinct characters.
 *
 * The remaining characters are in the same order as in the original string.
 */
fun String.distinct(): String = this.toList().distinct().joinToString("")