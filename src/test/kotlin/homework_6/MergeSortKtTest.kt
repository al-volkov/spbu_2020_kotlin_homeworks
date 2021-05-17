package homework_6

import homework_6.MergeSort.mergeSortMT
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

internal class MergeSortKtTest {
    companion object {
        private fun getRandomArray(size: Int) = IntArray(size) { Random.nextInt() }

        @JvmStatic
        fun inputData(): List<Arguments> {
            val list = mutableListOf<Arguments>()
            for (numberOfThreads in 1..10) {
                for (arraySize in 0..100) {
                    list.add(Arguments.of(getRandomArray(arraySize), numberOfThreads))
                }
            }
            return list
        }
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "mergeSortMt test {index}, {1}")
    fun mergeSortMTTest(arrayToSort: IntArray, numberOfThreads: Int) {
        val expectedArray = arrayToSort.clone()
        expectedArray.sort()
        val actualArray = arrayToSort.clone()
        actualArray.mergeSortMT(numberOfThreads)
        assertArrayEquals(expectedArray, actualArray)
    }
}
