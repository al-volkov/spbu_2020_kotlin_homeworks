package firsthomework

import java.lang.IndexOutOfBoundsException

private fun ArrayDeque<Int>.moveElement(startingIndex: Int, finalIndex: Int) {
    if (kotlin.math.max(startingIndex, finalIndex) >= this.size || kotlin.math.min(startingIndex, finalIndex) < 0) {
        throw IndexOutOfBoundsException("It is impossible to rearrange elements with such indexes.")
    }
    val valueOfElement = this[startingIndex]
    this.removeAt(startingIndex)
    this.add(finalIndex, valueOfElement)
}

interface Action {
    fun undo()
}

class PushForward(value: Int, private val storage: PerformedCommandStorage) : Action {
    init {
        storage.arrayDeque.addFirst(value)
        storage.addAction(this)
    }

    override fun undo() {
        storage.arrayDeque.removeFirst()
    }
}

class PushBack(value: Int, private val storage: PerformedCommandStorage) : Action {
    init {
        storage.arrayDeque.addLast(value)
        storage.addAction(this)
    }

    override fun undo() {
        storage.arrayDeque.removeLast()
    }
}

class MoveElement(
    private val startingIndex: Int,
    private val finalIndex: Int,
    private val storage: PerformedCommandStorage
) : Action {
    init {
        storage.arrayDeque.moveElement(startingIndex, finalIndex)
        storage.addAction(this)
    }

    override fun undo() {
        storage.arrayDeque.moveElement(finalIndex, startingIndex)
    }
}
