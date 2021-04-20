package homework_6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MergeSortKtTest {
    @Test
    fun mergeSortTest() {
        for (i in 1..1000) {
            val array1 = IntArray(i) { it }
            array1.shuffle()
            val array2 = array1.copyOf()
            array1.sort()
            array2.mergeSort()
            assertEquals(array1.toList(), array2.toList())
        }
    }

    @Test
    fun mergeSortMtTest() {
        for (numberOfThreads in 1..10) {
            for (i in 2..500) {
                val array1 = IntArray(i) { it }
                array1.shuffle()
                val array2 = array1.copyOf()
                array1.sort()
                array2.mergeSortMT(numberOfThreads = numberOfThreads)
                assertEquals(array1.toList(), array2.toList())
            }
        }
    }
}
