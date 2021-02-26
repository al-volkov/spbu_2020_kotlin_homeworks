package firsthomework

import java.lang.IndexOutOfBoundsException

fun ArrayDeque<Int>.moveElements(firstIndex: Int, secondIndex: Int) {
    if (kotlin.math.max(firstIndex, secondIndex) >= this.size || kotlin.math.min(firstIndex, secondIndex) < 0) {
        throw IndexOutOfBoundsException("It is impossible to rearrange elements with such indexes.")
    }
    var first = firstIndex
    var second = secondIndex
    if (first < second) {
        val temporary = first
        first = second
        second = temporary
    }
    if (first != second) {
        val temporary = this[first]
        this.removeAt(first)
        this.add(first, this[second])
        this.removeAt(second)
        this.add(second, temporary)
    }
}

interface Action {
    fun undo()
}

class PushForward(value: Int, private val storage: PerformedCommandStorage) : Action {
    init {
        storage.getDeque().addFirst(value)
        storage.getActions().addElement(this)
    }

    override fun undo() {
        storage.getDeque().removeFirst()
        storage.getActions().removeLast()
    }
}

class PushBack(value: Int, private val storage: PerformedCommandStorage) : Action {
    init {
        storage.getDeque().addLast(value)
        storage.getActions().addElement(this)
    }

    override fun undo() {
        storage.getDeque().removeLast()
        storage.getActions().removeLast()
    }
}

class MoveElements(
    private val firstIndex: Int,
    private val secondIndex: Int,
    private val storage: PerformedCommandStorage
) :
    Action {
    init {
        storage.getDeque().moveElements(firstIndex, secondIndex)
        storage.getActions().addElement(this)
    }

    override fun undo() {
        storage.getDeque().moveElements(firstIndex, secondIndex)
        storage.getActions().removeLast()
    }
}
