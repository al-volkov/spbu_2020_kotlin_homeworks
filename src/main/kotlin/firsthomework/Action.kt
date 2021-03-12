package firsthomework

import java.lang.IndexOutOfBoundsException

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
interface Action {
    fun undo()
}
/**
 * Class for adding numbers to the beginning of a list
 * @param value The value of the element we want to add
 * @property storage The [PerformedCommandStorage] we work with
 */
class PushForward(value: Int, private val storage: PerformedCommandStorage) : Action {
    /**
     * Adds an element to the beginning of the list
     */
    init {
        storage.arrayDeque.addFirst(value)
        storage.addAction(this)
    }
    /**
     * Removes an item from the beginning of the list
     */
    override fun undo() {
        storage.arrayDeque.removeFirst()
    }
}
/**
 * Class for adding numbers to the end of a list
 * @param value The value of the element we want to add
 * @property storage The [PerformedCommandStorage] we work with
 */
class PushBack(value: Int, private val storage: PerformedCommandStorage) : Action {
    /**
     * Adds an element to the end of the list
     */
    init {
        storage.arrayDeque.addLast(value)
        storage.addAction(this)
    }
    /**
     * Removes an item from the end of the list
     */
    override fun undo() {
        storage.arrayDeque.removeLast()
    }
}

/**
 * A class that allows you to shift an item in a list
 * @property startingIndex The initial position of the element
 * @property finalIndex End position of the element
 * @property storage The [PerformedCommandStorage] we work with
 */
class MoveElement(
    private val startingIndex: Int,
    private val finalIndex: Int,
    private val storage: PerformedCommandStorage
) : Action {
    /**
     * Moves an element from position startingIndex to position finalIndex
     */
    init {
        storage.arrayDeque.moveElement(startingIndex, finalIndex)
        storage.addAction(this)
    }
    /**
     * Moves an element from position finalIndex to position startingIndex
     */
    override fun undo() {
        storage.arrayDeque.moveElement(finalIndex, startingIndex)
    }
}
