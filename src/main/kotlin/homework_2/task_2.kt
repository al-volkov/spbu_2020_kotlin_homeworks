package homework_2

const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3

fun IntArray.removeDuplicateElements(): IntArray {
    val set: LinkedHashSet<Int> = linkedSetOf()
    set.addAll(this.toTypedArray())
    return set.toIntArray()
}

fun main() {
    val testArray = intArrayOf(NUMBER1, NUMBER2, NUMBER3, NUMBER3, NUMBER1)
    print(testArray.joinToString())
    print("\n")
    print(testArray.removeDuplicateElements().joinToString())
}
