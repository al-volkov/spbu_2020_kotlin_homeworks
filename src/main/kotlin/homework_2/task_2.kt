package homework_2

fun IntArray.removeDuplicateElements(): IntArray {
    val set: LinkedHashSet<Int> = linkedSetOf()
    set.addAll(this.reversed().toTypedArray())
    return set.reversed().toIntArray()
}

fun getArray(): IntArray {
    print("Enter the number of elements\n")
    val scanner = java.util.Scanner(System.`in`)
    val size = scanner.nextInt()
    println("Enter the elements")
    return IntArray(size) { scanner.nextInt() }
}

fun main() {
    val array = getArray()
    print("Array without duplicate elements:" + array.removeDuplicateElements().joinToString(" "))
}
