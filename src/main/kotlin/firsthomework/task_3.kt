package firsthomework

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4

fun main() {
    val testObject = PerformedCommandStorage()
    testObject.undo()
    testObject.pushFront(NUMBER2)
    testObject.print()
    testObject.pushFront(NUMBER1)
    testObject.print()
    testObject.pushBack(NUMBER3)
    testObject.print()
    testObject.pushBack(NUMBER4)
    testObject.print()
    testObject.moveElement(NUMBER3, NUMBER0)
    testObject.print()
    testObject.moveElement(NUMBER1, NUMBER2)
    testObject.print()
    repeat(NUMBER3 + NUMBER4) {
        testObject.undo()
        testObject.print()
    }
}
