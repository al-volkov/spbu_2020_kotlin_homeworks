package firsthomework

fun main() {
    val newObject = PerformedCommandStorage()
    newObject.test()
    newObject.pushFront(1)
    println(newObject.getSize())
}
