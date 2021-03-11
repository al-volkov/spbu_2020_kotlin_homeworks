package firsthomework

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4
const val NUMBER5 = 5

fun main() {
    val testStorage1 = PerformedCommandStorage()
    testStorage1.undo()
    testStorage1.print()
    PushForward(NUMBER2).execute(testStorage1)
    testStorage1.print()
    PushForward(NUMBER1).execute(testStorage1)
    testStorage1.print()
    PushBack(NUMBER3).execute(testStorage1)
    testStorage1.print()
    PushBack(NUMBER4).execute(testStorage1)
    testStorage1.print()
    MoveElement(NUMBER0, NUMBER3).execute(testStorage1)
    testStorage1.print()
    MoveElement(NUMBER2, NUMBER0).execute(testStorage1)
    testStorage1.print()
    MoveElement(NUMBER1, NUMBER2).execute(testStorage1)
    println("State of the first list at the time of serialization:")
    testStorage1.print()
    testStorage1.serialize("testfile.json")
    val testStorage2 = PerformedCommandStorage()
    testStorage2.deserialize("testfile.json")
    println("State of the second list after deserialization:")
    testStorage2.print()
    println("Actions are now undone:")
    repeat(NUMBER5 + NUMBER2) {
        testStorage1.undo()
        testStorage1.print()
    }
}
