package firsthomework

fun main() {
    val testObject = PerformedCommandStorage()
    testObject.undo()
    testObject.pushFront(2)
    testObject.print()
    testObject.pushFront(1)
    testObject.print()
    testObject.pushBack(3)
    testObject.print()
    testObject.pushBack(4)
    testObject.print()
    testObject.moveElement(3,0)
    testObject.print()
    testObject.moveElement(1,2)
    testObject.print()
    repeat(7){
        testObject.undo()
        testObject.print()
    }
}
