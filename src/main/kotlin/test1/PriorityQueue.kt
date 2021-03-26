package test1

class PriorityQueue<E, K : Comparable<K>> {
    data class PriorityQueueElement<E, K>(val element: E, val priority: K)

    private val list: MutableList<PriorityQueueElement<E, K>> = mutableListOf()

    fun enqueue(element: E, priority: K) {
        list.add(PriorityQueueElement(element, priority))
        list.sortBy { it.priority }
    }

    fun peek(): E {
        if (this.isEmpty()) {
            throw NoSuchElementException("Queue is empty\n")
        }
        return list.last().element
    }

    fun remove() {
        if (this.isEmpty()) {
            throw NoSuchElementException("Queue is empty\n")
        }
        list.removeLast()
    }

    fun rool() : E {
        if (this.isEmpty()) {
            throw NoSuchElementException("Queue is empty\n")
        }
        return list.removeLast().element
    }
}
