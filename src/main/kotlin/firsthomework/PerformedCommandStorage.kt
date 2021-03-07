package firsthomework

import kotlin.collections.ArrayDeque

/**
 * Stores a list of numbers and actions on it
 * @property arrayDeque Public property for accessing [_arrayDeque]
 * @property _arrayDeque stores numbers
 * @property actions keeps performed actions
 */
class PerformedCommandStorage {
    val arrayDeque: ArrayDeque<Int>
        get() {
            return _arrayDeque
        }
    private val _arrayDeque: ArrayDeque<Int> = ArrayDeque()
    private val actions: java.util.Stack<Action> = java.util.Stack()

    /**
     * adds and [action] to [actions]
     */
    fun addAction(action: Action) = actions.addElement(action)

    /**
     * calls undo() on the last element if [actions] and removes it
     */
    fun undo() {
        if (actions.isEmpty()) {
            println("No action has been taken yet.")
        } else {
            actions.lastElement().undo()
            actions.removeLast()
        }
    }

    /**
     * prints all the elements in the list
     */
    fun print() {
        if (this._arrayDeque.size == 0) {
            println("No elements (empty queue)")
        } else {
            println(this._arrayDeque.joinToString(" "))
        }
    }
}
