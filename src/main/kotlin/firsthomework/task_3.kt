package firsthomework

fun main() {
    val testNumbers: IntArray = intArrayOf(1, 2, 3, 4)
    val testObject = PerformedCommandStorage()
    testObject.undo()
    testObject.pushFront(testNumbers[1])
    testObject.print()
    testObject.pushFront(testNumbers[0])
    testObject.print()
    testObject.pushBack(testNumbers[2])
    testObject.print()
    testObject.pushBack(testNumbers[3])
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
