package homework_1

import java.lang.IndexOutOfBoundsException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Moves an element from position [startingIndex] to position [finalIndex]
 * @param startingIndex The initial position of the element
 * @param finalIndex End position of the element
 */
private fun ArrayDeque<Int>.moveElement(startingIndex: Int, finalIndex: Int) {
    if (kotlin.math.max(startingIndex, finalIndex) >= this.size || kotlin.math.min(startingIndex, finalIndex) < 0) {
        throw IndexOutOfBoundsException("It is impossible to rearrange elements with such indexes.")
    }
    val valueOfElement = this[startingIndex]
    this.removeAt(startingIndex)
    this.add(finalIndex, valueOfElement)
}

/**
 * For actions on a list of numbers that can be undone
 */
@Serializable
sealed class Action {
    abstract fun execute(storage: PerformedCommandStorage)
    abstract fun undo(storage: PerformedCommandStorage)
}

/**
 * Class for adding numbers to the beginning of a list
 * @property value The value of the element we want to add
 */
@Serializable
@SerialName("PushForward")
class PushForward(private val value: Int) : Action() {
    /**
     * Adds an element to the beginning of the list
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.addFirst(value)
        storage.addAction(this)
    }

    /**
     * Removes an item from the beginning of the list
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun undo(storage: PerformedCommandStorage) {
        storage.arrayDeque.removeFirst()
    }
}

/**
 * Class for adding numbers to the end of a list
 * @property value The value of the element we want to add
 */
@Serializable
@SerialName("PushBack")
class PushBack(private val value: Int) : Action() {
    /**
     * Adds an element to the end of the list
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.addLast(value)
        storage.addAction(this)
    }

    /**
     * Removes an item from the end of the list
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun undo(storage: PerformedCommandStorage) {
        storage.arrayDeque.removeLast()
    }
}

/**
 * A class that allows you to shift an item in a list
 * @property startingIndex The initial position of the element
 * @property finalIndex End position of the element
 */
@Serializable
@SerialName("MoveElement")
class MoveElement(private val startingIndex: Int, private val finalIndex: Int) : Action() {
    /**
     * Moves an element from position startingIndex to position finalIndex
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.moveElement(startingIndex, finalIndex)
        storage.addAction(this)
    }

    /**
     * Moves an element from position finalIndex to position startingIndex
     * @param storage The [PerformedCommandStorage] we work with
     */
    override fun undo(storage: PerformedCommandStorage) {
      storage.arrayDeque.moveElement(finalIndex, startingIndex)
    }
}
