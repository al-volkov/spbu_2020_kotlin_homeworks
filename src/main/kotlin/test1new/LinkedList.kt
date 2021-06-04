package test1new

class LinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    private class Node<T>(
        val value: T,
        private var previousPrivate: Node<T>?,
        private var nextPrivate: Node<T>?
    ) {
        val previous
            get() = previousPrivate
        val next
            get() = nextPrivate

        fun changePrevious(newPrevious: Node<T>?) {
            previousPrivate = newPrevious
        }

        fun changeNext(newNext: Node<T>?) {
            nextPrivate = newNext
        }
    }

    private fun getElement(position: Int): Node<T> {
        var currentElement = head
        repeat(position) {
            currentElement = currentElement?.next
                ?: throw NullPointerException("A null pointer occurred while searching for an element in the list")
        }
        return currentElement
            ?: throw NullPointerException("A null pointer occurred while searching for an element in the list")
    }

    fun isEmpty() = size == 0

    fun add(value: T) {
        if (tail == null) {
            tail = Node(value, null, null)
            head = tail
        } else {
            val newNode = Node(value, tail, null)
            tail?.changeNext(newNode)
            tail = newNode
        }
        ++size
    }

    fun add(value: T, position: Int) {
        if (position < 0 || position > size) {
            throw IndexOutOfBoundsException(
                "List size is $size. It is impossible to add element at position $position"
            )
        }
        ++size
        when (position) {
            0 -> {
                val newNode = Node(value, null, head)
                head?.changePrevious(newNode)
                head = newNode
            }
            size -> {
                val newNode = Node(value, tail, null)
                tail?.changeNext(newNode)
                tail = newNode
            }
            else -> {
                val elementToAdd = Node(value, null, null)
                val previousElement = getElement(position - 1)
                elementToAdd.changePrevious(previousElement)
                elementToAdd.changeNext(previousElement.next)
                previousElement.changeNext(elementToAdd)
                elementToAdd.next?.changePrevious(elementToAdd)
            }
        }
    }

    fun remove(position: Int) {
        if (position < 0 || position > size - 1) {
            throw IndexOutOfBoundsException(
                "List size is $size. It is impossible to remove the element at position $position"
            )
        }
        --size
        if (position == 0) {
            head = head?.next
            if (size == 0) {
                tail = null
            }
            return
        }
        if (position == size - 1) {
            tail = tail?.previous
            return
        }
        val elementToRemove = getElement(position)
        elementToRemove.previous?.changeNext(elementToRemove.next)
        elementToRemove.next?.changePrevious(elementToRemove.previous)
    }

    fun get(): T = head?.value
        ?: throw IndexOutOfBoundsException(
            "The list is empty. Cannot get the value of the element at the beginning of the list"
        )

    fun get(position: Int): T {
        if (position < 0 || position > size - 1) {
            throw IndexOutOfBoundsException(
                "List size is $size. It is impossible to get the value of the element at position $position"
            )
        }
        return getElement(position).value
    }
}
