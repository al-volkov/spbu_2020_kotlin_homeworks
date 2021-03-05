package homework_2

fun Array<Int>.removeDuplicateElements(): Array<Int> {
    val set: LinkedHashSet<Int> = linkedSetOf()
    set.addAll(this)
    return set.toTypedArray()
}

fun main() {
    val testArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 6, 7)
    print(testArray.joinToString())
    print("\n")
    print(testArray.removeDuplicateElements().joinToString())
}
