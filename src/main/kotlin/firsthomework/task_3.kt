package firsthomework

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4
const val NUMBER5 = 5

fun main() {
    val testStorage = PerformedCommandStorage()
    testStorage.undo()
    PushForward(NUMBER2, testStorage)
    testStorage.print()
    PushForward(NUMBER1, testStorage)
    testStorage.print()
    PushBack(NUMBER3, testStorage)
    testStorage.print()
    PushBack(NUMBER4, testStorage)
    testStorage.print()
    MoveElements(NUMBER1, NUMBER2, testStorage)
    testStorage.print()
    MoveElements(NUMBER3, NUMBER0, testStorage)
    testStorage.print()
    repeat(NUMBER5) {
        testStorage.undo()
        testStorage.print()
    }
}
