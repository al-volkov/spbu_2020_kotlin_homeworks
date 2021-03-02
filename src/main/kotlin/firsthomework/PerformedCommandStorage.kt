package firsthomework

import kotlin.collections.ArrayDeque

class PerformedCommandStorage {
    val arrayDeque: ArrayDeque<Int>
        get() {
            return _arrayDeque
        }
    private val _arrayDeque: ArrayDeque<Int> = ArrayDeque()
    private val actions: java.util.Stack<Action> = java.util.Stack()
    fun addAction(action: Action) = actions.addElement(action)
    fun undo() {
        if (actions.isEmpty()) {
            println("No action has been taken yet.")
        } else {
            actions.lastElement().undo()
            actions.removeLast()
        }
    }

    fun print() {
        if (this._arrayDeque.size == 0) {
            println("No elements (empty queue)")
        } else {
            println(this._arrayDeque.joinToString(" "))
        }
    }
}
