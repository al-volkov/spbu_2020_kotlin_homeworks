package test3

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BubbleSortKtTest {
    class SimpleIntComparator : Comparator<Int> {
        override fun compare(o1: Int?, o2: Int?): Int {
            return if (o1 != null && o2 != null) {
                (o1 - o2)
            } else {
                0
            }
        }
    }

    class SimpleStringComparator : Comparator<String> {
        override fun compare(o1: String?, o2: String?): Int {
            return if (o1 != null && o2 != null) {
                o1.length - o2.length
            } else {
                0
            }
        }
    }

    companion object {
        @JvmStatic
        fun intTests(): List<Arguments> = listOf(
            Arguments.of(listOf(3, 9, -5), intArrayOf(-5, 3, 9), SimpleIntComparator()),
            Arguments.of(setOf(-99, -100, 0), intArrayOf(-100, -99, 0), SimpleIntComparator()),
            Arguments.of(arrayListOf(3, 1, 2), intArrayOf(1, 2, 3), SimpleIntComparator())
        )

        @JvmStatic
        fun stringTests(): List<Arguments> = listOf(
            Arguments.of(listOf("aa", "a", "aaa"), arrayOf("a", "aa", "aaa"), SimpleStringComparator()),
            Arguments.of(setOf("char", "string", "int"), arrayOf("int", "char", "string"), SimpleStringComparator()),
            Arguments.of(arrayListOf("bbb", "bb", "b"), arrayOf("b", "bb", "bbb"), SimpleStringComparator())
        )
    }

    @MethodSource("intTests")
    @ParameterizedTest(name = "int test {index}, {1}")
    fun intCollectionsTest(collectionToSort: Collection<Int>, expectedArray: IntArray, comparator: Comparator<Int>) {
        assertArrayEquals(expectedArray.toTypedArray(), collectionToSort.bubbleSort(comparator))
    }

    @MethodSource("stringTests")
    @ParameterizedTest(name = "string test {index}, {1}")
    fun stringCollectionsTest(
        collectionToSort: Collection<String>,
        expectedArray: Array<String>,
        comparator: Comparator<String>
    ) {
        assertArrayEquals(expectedArray, collectionToSort.bubbleSort(comparator))
    }
}
