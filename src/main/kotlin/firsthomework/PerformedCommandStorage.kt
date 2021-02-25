package firsthomework

class PerformedCommandStorage {
    fun print() {
        var currentElement = this.head
        repeat(size) {
            print("${currentElement!!.getValue()} ")
            currentElement = currentElement!!.getNext()
        }
        println()
    }

    fun pushFront(value: Int) {
        actions.add(0, Action(ActionType.PUSHFRONT))
        if (this.isEmpty()) {
            head = Element(value)
            tail = head
        } else {
            val newElement = Element(value, head)
            head!!.changePrevious(newElement)
            head = newElement
        }
        ++size
    }

    fun pushBack(value: Int) {
        actions.add(0, Action(ActionType.PUSHBACK))
        if (this.isEmpty()) {
            tail = Element(value)
            head = tail
        } else {
            val newElement = Element(value, null, tail)
            tail!!.changeNext(newElement)
            tail = newElement
        }
        ++size
    }

    fun moveElement(startingIndex: Int, finalIndex: Int) {
        if (kotlin.math.max(startingIndex, finalIndex) >= size || kotlin.math.min(startingIndex, finalIndex) < 0) {
            throw IndexOutOfBoundsException()
        } else {
            actions.add(0, Action(ActionType.MOVE, startingIndex, finalIndex))
            var firstElement = head
            var secondElement = head
            repeat(startingIndex) {
                firstElement = firstElement!!.getNext()
            }
            repeat(finalIndex) {
                secondElement = secondElement!!.getNext()
            }
            val temporary = firstElement!!.getValue()
            firstElement!!.changeValue(secondElement!!.getValue())
            secondElement!!.changeValue(temporary)
        }
    }

    fun undo() {
        if (actions.isEmpty()) {
            println("No action has been taken yet")
            return
        }
        when (actions.firstElement().getType()) {
            ActionType.PUSHFRONT -> popFront()
            ActionType.PUSHBACK -> popBack()
            ActionType.MOVE -> {
                moveElement(
                    actions.firstElement().getFirstIndex(),
                    actions.firstElement().getSecondIndex()
                )
                actions.removeAt(0)
            }
        }
        actions.removeAt(0)
    }

    private fun popBack() {
        if (size == 1) {
            tail = null
            head = null
        } else {
            tail!!.getPrevious()!!.changeNext(null)
            tail = tail!!.getPrevious()
        }
        --size
    }

    private fun popFront() {
        if (size == 1) {
            tail = null
            head = null
        } else {
            head!!.getNext()!!.changePrevious(null)
            head = head!!.getNext()
        }
        --size
    }

    private fun isEmpty(): Boolean {
        return size == 0
    }

    private enum class ActionType {
        PUSHBACK,
        PUSHFRONT,
        MOVE
    }

    private class Action(type: ActionType, index1: Int = 0, index2: Int = 0) {
        fun getType() = type
        fun getFirstIndex() = index1
        fun getSecondIndex() = index2
        private val type = type
        private val index1 = index1
        private val index2 = index2
    }

    private class Element(value: Int, nextElement: Element? = null, previousElement: Element? = null) {
        fun getNext() = this.nextElement

        fun getPrevious() = this.previousElement

        fun getValue() = this.value

        fun changeNext(nextElement: Element?) {
            this.nextElement = nextElement
        }

        fun changePrevious(previousElement: Element?) {
            this.previousElement = previousElement
        }

        fun changeValue(newValue: Int) {
            this.value = newValue
        }

        private var value = value
        private var nextElement: Element? = nextElement
        private var previousElement: Element? = previousElement
    }

    private var size: Int = 0
    private var head: Element? = null
    private var tail: Element? = null
    private var actions: java.util.Vector<Action> = java.util.Vector()
}
