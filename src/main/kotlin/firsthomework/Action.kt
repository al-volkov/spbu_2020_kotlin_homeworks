package firsthomework

import java.lang.IndexOutOfBoundsException
import kotlinx.serialization.Serializable

private fun ArrayDeque<Int>.moveElement(startingIndex: Int, finalIndex: Int) {
    if (kotlin.math.max(startingIndex, finalIndex) >= this.size || kotlin.math.min(startingIndex, finalIndex) < 0) {
        throw IndexOutOfBoundsException("It is impossible to rearrange elements with such indexes.")
    }
    val valueOfElement = this[startingIndex]
    this.removeAt(startingIndex)
    this.add(finalIndex, valueOfElement)
}

@Serializable
sealed class Action {
    abstract fun execute(storage: PerformedCommandStorage)
    abstract fun undo(storage: PerformedCommandStorage)
}

@Serializable
class PushForward(private val value: Int) : Action() {
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.addFirst(value)
        storage.addAction(this)
    }

    override fun undo(storage: PerformedCommandStorage) {
        storage.arrayDeque.removeFirst()
    }
}

@Serializable
class PushBack(private val value: Int) : Action() {
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.addLast(value)
        storage.addAction(this)
    }

    override fun undo(storage: PerformedCommandStorage) {
        storage.arrayDeque.removeLast()
    }
}

@Serializable
class MoveElement(private val startingIndex: Int, private val finalIndex: Int) : Action() {
    override fun execute(storage: PerformedCommandStorage) {
        storage.arrayDeque.moveElement(startingIndex, finalIndex)
        storage.addAction(this)
    }

    override fun undo(storage: PerformedCommandStorage) {
        storage.arrayDeque.moveElement(finalIndex, startingIndex)
    }
}
