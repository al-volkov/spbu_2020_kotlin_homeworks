package firsthomework

import kotlinx.serialization.decodeFromString
import kotlin.collections.ArrayDeque
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter

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
            actions.lastElement().undo(this)
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

    /**
     * Serializes [actions] and places in file
     * @param path path to the file where the data will be saved
     */
    fun serialize(path: String) {
        val temporaryList = actions.toList()
        val stringInJsonFormat = Json.encodeToString(temporaryList)
        val newFile = FileWriter(path)
        newFile.write(stringInJsonFormat)
        newFile.flush()
        newFile.close()
    }

    /**
     * Deserializes a list with actions and applies them to the [arrayDeque]
     * @param path path to the file from which the data is taken
     */
    fun deserialize(path: String) {
        val stringInJsonFormat = File(path).readText(Charsets.UTF_8)
        val listWithActions = Json.decodeFromString<List<Action>>(stringInJsonFormat)
        for (action in listWithActions) {
            action.execute(this)
        }
    }
}
