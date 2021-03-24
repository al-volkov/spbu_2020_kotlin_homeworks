@file:Suppress("MagicNumber")

package homework_1

fun main() {
    val fileName = "src/main/resources/homework_1/testfile.json"
    val number0 = 0
    val number1 = 1
    val number2 = 2
    val number3 = 3
    val number4 = 4
    val number5 = 5
    val testStorage1 = PerformedCommandStorage()
    testStorage1.undo()
    testStorage1.print()
    PushForward(number2).execute(testStorage1)
    testStorage1.print()
    PushForward(number1).execute(testStorage1)
    testStorage1.print(
    PushBack(number3).execute(testStorage1)
    testStorage1.print()
    PushBack(number4).execute(testStorage1)
    testStorage1.print()
    MoveElement(number0, number3).execute(testStorage1)
    testStorage1.print()
    MoveElement(number2, number0).execute(testStorage1)
    testStorage1.print()
    MoveElement(number1, number2).execute(testStorage1)
    println("State of the first list at the time of serialization:")
    testStorage1.print()
    testStorage1.serialize(fileName)
    val testStorage2 = PerformedCommandStorage()
    testStorage2.deserialize(fileName)
    println("State of the second list after deserialization:")
    testStorage2.print()
    println("Actions are now undone:")
    repeat(number5 + number2) {
        testStorage1.undo()
        testStorage1.print()
    }
}
