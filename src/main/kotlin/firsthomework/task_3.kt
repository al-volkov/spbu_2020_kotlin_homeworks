package firsthomework

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
    testObject.moveElement(3, 0)
    testObject.print()
    testObject.moveElement(1, 2)
    testObject.print()
    repeat(7) {
        testObject.undo()
        testObject.print()
    }
}
