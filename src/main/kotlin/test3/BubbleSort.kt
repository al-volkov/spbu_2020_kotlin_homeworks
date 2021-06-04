package test3

import java.lang.ArithmeticException
import java.lang.IndexOutOfBoundsException

/**
 * sorts iterable collection and returns typed array
 * [comparator] - comparator for elements
 */
inline fun <reified T> Iterable<T>.bubbleSort(comparator: Comparator<T>): Array<T> {
    val list = mutableListOf<T>()
    this.forEach { list.add(it) }
    for (i in 0 until (list.size - 1)) {
        for (j in 0 until (list.size - 1 - i)) {
            handleComparison(list, comparator, j, j + 1)
        }
    }
    return list.toTypedArray()
}

@Suppress("SwallowedException", "TooGenericExceptionCaught")
fun <T> handleComparison(list: MutableList<T>, comparator: Comparator<T>, index1: Int, index2: Int) {
    try {
        if (comparator.compare(list[index1], list[index2]) > 0) {
            list[index1] = list[index2].also { list[index2] = list[index1] }
        }
    } catch (e: ArithmeticException) {
        throw ArithmeticException("Arithmetic error occurred while comparing elements:" + e.localizedMessage)
    } catch (e: IndexOutOfBoundsException) {
        throw IndexOutOfBoundsException("Index error occurred while comparing elements:" + e.localizedMessage)
    }
}
