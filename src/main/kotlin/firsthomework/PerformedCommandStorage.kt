package firsthomework

class PerformedCommandStorage {
    fun test() {
        println("Bip bop bip")
    }

    fun pushFront(value: Int) {
        val newElement = ListElement(value, if (isEmpty()) null else head)
        head = newElement
        ++size
    }

    fun pushBack(value: Int) {
        val newElement = ListElement(value, null)
    }

    fun moveElement(startingIndex: Int, finalIndex: Int) {
    }

    fun getSize() = size

    private fun isEmpty(): Boolean {
        return size == 0
    }

    private class ListElement(value: Int, nextElement: ListElement?) {
        var value = value
        var nextElement = nextElement
    }

    private var size: Int = 0
    private var head: ListElement? = null
}
