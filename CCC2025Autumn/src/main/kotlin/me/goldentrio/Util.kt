package me.goldentrio

import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

data class Coordinates(var x: Int = 0, var y: Int = 0) {

    fun collidesWith(other: Coordinates, box: Int): Boolean {
        val deltaX = abs(x - other.x)
        val deltaY = abs(y - other.y)

        return deltaX <= box && deltaY <= box
    }

    fun absoluteDistance(other: Coordinates): Double {
        return sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2))
    }

}

enum class Direction(val sign: Int) {

    POSITIVE(1),
    NEGATIVE(-1),
    STAY(0);

    operator fun unaryMinus(): Direction = when (this) {
        POSITIVE -> Direction.NEGATIVE
        NEGATIVE -> Direction.POSITIVE
        STAY -> Direction.STAY
    }

    companion object {

        fun towards(current: Int, goal: Int, orElse: Direction = Direction.STAY) = when ((goal - current).sign) {
            -1 -> NEGATIVE
            0 -> orElse
            1 -> POSITIVE
            else -> throw IllegalStateException("Sign must be -1, 0 or 1")
        }

    }

}

enum class Axis {
    X,
    Y
}

class Pace {

    var pace: Int = 0
        private set

    private var startTime: Int = 0

    val currentDirection
        get() = when {
            pace < 0 -> Direction.NEGATIVE
            pace > 0 -> Direction.POSITIVE
            else -> Direction.STAY
        }

    fun faster(direction: Direction) {
        if (direction == Direction.STAY) return

        if (pace.sign != 0 && pace.sign != direction.sign) {
            slower(-direction)
            return
        }

        if (pace.absoluteValue == 1) return

        if (pace == 0) {
            pace = when(direction) {
                Direction.POSITIVE -> 5
                Direction.NEGATIVE -> -5
                else -> 0
            }
            return
        }

        pace -= direction.sign
    }

    fun slower(direction: Direction) {
        if (direction == Direction.STAY) return

        if (pace.sign != 0 && pace.sign != direction.sign) {
            faster(-direction)
            return
        }

        if (pace == 0) return

        if (pace.absoluteValue == 5) {
            pace = 0
            return
        }

        pace += direction.sign
    }

    fun start(time: Int) {
        startTime = time
    }

    fun shouldMove(time: Int): Boolean {
        return abs(time - startTime) == pace.absoluteValue
    }

    override fun toString(): String {
        return "Pace($pace)"
    }

    override fun hashCode(): Int {
        var result = pace
        result = 31 * result + startTime
        return result
    }

}

data class Movement(val x: Pace = Pace(), val y: Pace = Pace()) {

    fun faster(axis: Axis, direction: Direction) {
        (if (axis == Axis.X) x else y).faster(direction)
    }

    fun slower(axis: Axis, direction: Direction) {
        (if (axis == Axis.X) x else y).slower(direction)
    }

    fun faster(axis: Axis) {
        val pace = if (axis == Axis.X) x else y
        pace.faster(pace.currentDirection)
    }

    fun slower(axis: Axis) {
        val pace = if (axis == Axis.X) x else y
        pace.faster(pace.currentDirection)
    }

    fun paceInAxis(axis: Axis) = if (axis == Axis.X) x else y

}