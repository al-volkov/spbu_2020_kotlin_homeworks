package homework_6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MergeSortKtTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), intArrayOf(10, 6, 7, 4, 3, 5, 8, 1, 9, 2)),
            Arguments.of(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), intArrayOf(6, 9, 1, 8, 5, 3, 4, 2, 7, 10)),
            Arguments.of(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), intArrayOf(9, 6, 7, 10, 4, 2, 8, 3, 1, 5)),
            Arguments.of(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), intArrayOf(5, 6, 7, 2, 3, 8, 1, 10, 9, 4)),
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "mergeSort test {index}, {1}")
    fun mergeSortTest(expectedList: List<Int>, actualArray: IntArray) {
        actualArray.mergeSort()
        assertEquals(expectedList, actualArray.toList())
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "mergeSortMt test {index}, {1}")
    fun mergeSortMtTest(expectedList: List<Int>, actualArray: IntArray) {
        for (numberOfThreads in 1..10) {
            actualArray.mergeSortMT(numberOfThreads = numberOfThreads)
            try {
                assert(expectedList == actualArray.toList())
            } catch (e: AssertionError) {
                throw AssertionError(
                    "expected: $expectedList\nactual: ${actualArray.toList()}\nnumber of threads = $numberOfThreads"
                )
            }
        }
    }
}
