package me.task.util.tupple

/**
 * A mutable pair of two values.
 */
class MutablePair<A, B>(
    var first: A,
    var second: B
) {

    override fun toString(): String = "($first, $second)"

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is Pair<*, *>) return first == other.first && second == other.second

        if (other !is MutablePair<*, *>) return false

        return first == other.first && second == other.second
    }

    override fun hashCode(): Int {
        var result = first?.hashCode() ?: 0
        result = 31 * result + (second?.hashCode() ?: 0)
        return result
    }

}
