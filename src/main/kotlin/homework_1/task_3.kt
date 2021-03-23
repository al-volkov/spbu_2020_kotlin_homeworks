package homework_1

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4
const val NUMBER5 = 5

fun main() {
    val testStorage = PerformedCommandStorage()
    testStorage.undo()
    testStorage.print()
    PushForward(NUMBER2, testStorage)
    testStorage.print()
    PushForward(NUMBER1, testStorage)
    testStorage.print()
    PushBack(NUMBER3, testStorage)
    testStorage.print()
    PushBack(NUMBER4, testStorage)
    testStorage.print()
    MoveElement(NUMBER0, NUMBER3, testStorage)
    testStorage.print()
    MoveElement(NUMBER2, NUMBER0, testStorage)
    testStorage.print()
    MoveElement(NUMBER1, NUMBER2, testStorage)
    testStorage.print()
    repeat(NUMBER5 + NUMBER2) {
        testStorage.undo()
        testStorage.print()
    }
}
