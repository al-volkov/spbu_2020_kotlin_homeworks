package firsthomework

import kotlin.collections.ArrayDeque

class PerformedCommandStorage {
    private var arrayDeque: ArrayDeque<Int> = ArrayDeque()
    private var actions: java.util.Stack<Action> = java.util.Stack()
    fun getDeque() = arrayDeque
    fun getActions() = actions
    fun undo() {
        if (actions.isEmpty()) {
            println("No action has been taken yet.")
        } else {
            actions.lastElement().undo()
        }
    }

    fun print() {
        println(this.arrayDeque.joinToString(" "))
    }
}
