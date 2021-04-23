package homework_6

import homework_6.MergeSort.mergeSortMT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MergeSortKtTest {
    companion object {
        private fun getRandomArray(size: Int): IntArray {
            val array = IntArray(size) { it }
            array.shuffle()
            return array
        }

        @JvmStatic
        fun inputData(): List<Arguments> {
            val list = mutableListOf<Arguments>()
            for (numberOfThreads in 1..10) {
                for (arraySize in 0..100) {
                    list.add(Arguments.of(List(arraySize) { it }, getRandomArray(arraySize), numberOfThreads))
                }
            }
            return list
        }
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "mergeSortMt test {index}, {1}")
    fun mergeSortMTTest(expectedList: List<Int>, actualArray: IntArray, numberOfThreads: Int) {
        actualArray.mergeSortMT(numberOfThreads)
        assertEquals(expectedList, actualArray.toList())
    }
}
