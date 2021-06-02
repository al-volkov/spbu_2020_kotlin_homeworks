@file:Suppress("LongParameterList")

package homework_6

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class MergeSorter {

    abstract val nameOfMethodUsed: String

    /**
     * Binary search returns :
     * [left] when segment is empty ([left] > [right])
     * [left] when [value] is less or equal to all elements in segment
     * in other cases it returns the largest index in the range [[left], [right] + 1] such
    that previous element (to the element with the found index) is less than [value]
     * @param value - the value based on which the search will be performed
     * @param left - start of the segment where the search will take place
     * @param right - end of the segment where the search will take place
     */
    private fun IntArray.binarySearch(value: Int, left: Int, right: Int): Int {
        var low = left
        var high = kotlin.math.max(left, right + 1)
        var mid: Int
        while (low < high) {
            mid = (low + high) / 2
            if (value <= this[mid]) {
                high = mid
            } else {
                low = mid + 1
            }
        }
        return high
    }

    protected data class Subarray(val leftBound: Int, val rightBound: Int)

    /**
     * Calls the recursive function
     * */
    fun sort(array: IntArray, numberOfThreads: Int) {
        val temporaryArray = IntArray(array.size) { 0 }
        array.mergeSortRecursive(Subarray(0, array.lastIndex), temporaryArray, 0, numberOfThreads)
        temporaryArray.copyInto(array)
    }

    protected fun IntArray.merge(
        subarray1: Subarray,
        subarray2: Subarray,
        resultArray: IntArray,
        leftBoundOfSubarrayToPutElements: Int,
        numberOfThreads: Int = 1
    ) {
        val numberOfElementsInFirstSubarray = subarray1.rightBound - subarray1.leftBound + 1
        val numberOfElementsInSecondSubarray = subarray2.rightBound - subarray2.leftBound + 1
        if (numberOfElementsInFirstSubarray < numberOfElementsInSecondSubarray) {
            this.merge(
                Subarray(subarray2.leftBound, subarray2.rightBound),
                Subarray(subarray1.leftBound, subarray1.rightBound),
                resultArray, leftBoundOfSubarrayToPutElements
            )
            return
        }
        if (numberOfElementsInFirstSubarray == 0) {
            return
        } else {
            val mid1 = (subarray1.leftBound + subarray1.rightBound) / 2 // middle of the first subarray
            val mid2 =
                this.binarySearch(this[mid1], subarray2.leftBound, subarray2.rightBound) // see binarySearch comments
            val mid3 = leftBoundOfSubarrayToPutElements + (mid1 - subarray1.leftBound) +
                    (mid2 - subarray2.leftBound) // index in resultArray, see next line
            resultArray[mid3] = this[mid1]
            if (numberOfThreads <= 1) {
                this.merge(
                    Subarray(subarray1.leftBound, mid1 - 1),
                    Subarray(subarray2.leftBound, mid2 - 1),
                    resultArray, leftBoundOfSubarrayToPutElements
                )
                this.merge(
                    Subarray(mid1 + 1, subarray1.rightBound),
                    Subarray(mid2, subarray2.rightBound),
                    resultArray, mid3 + 1
                )
            } else {
                val numberOfThreadsForLeftPart = numberOfThreads / 2
                val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                this.launchParallelMerge(
                    subarray1,
                    subarray2,
                    mid1,
                    mid2,
                    mid3,
                    resultArray,
                    leftBoundOfSubarrayToPutElements,
                    numberOfThreadsForLeftPart,
                    numberOfThreadsForRightPart,
                )
            }
        }
    }

    protected abstract fun IntArray.launchParallelMerge(
        subarray1: Subarray,
        subarray2: Subarray,
        mid1: Int,
        mid2: Int,
        mid3: Int,
        resultArray: IntArray,
        leftBoundOfSubarrayToPutElements: Int,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int
    )

    /**
     * sorts the array (or subarray) and puts the result in another array
     * works asynchronously or multithreaded
     * [subarray] subarray that will be sorted
     * [resultArray] an array where the elements will be placed
     * [leftBoundOfSubarrayToPutElements] the index from which the elements will be placed
     * [numberOfThreads] number of threads or coroutines
     */
    protected fun IntArray.mergeSortRecursive(
        subarray: Subarray,
        resultArray: IntArray,
        leftBoundOfSubarrayToPutElements: Int,
        numberOfThreads: Int
    ) {
        when (val numberOfElements = subarray.rightBound - subarray.leftBound + 1) {
            0 -> return
            1 -> {
                resultArray[leftBoundOfSubarrayToPutElements] = this[subarray.leftBound]
            }
            else -> {
                val temporaryArray = IntArray(numberOfElements) { 0 }
                val mid = (subarray.leftBound + subarray.rightBound) / 2
                val newMid = mid - subarray.leftBound
                if (numberOfThreads <= 1) {
                    this.mergeSortRecursive(Subarray(subarray.leftBound, mid), temporaryArray, 0, 1)
                    this.mergeSortRecursive(Subarray(mid + 1, subarray.rightBound), temporaryArray, newMid + 1, 1)
                } else {
                    val numberOfThreadsForLeftPart = numberOfThreads / 2
                    val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                    this.launchParallelSort(
                        subarray,
                        mid,
                        newMid,
                        temporaryArray,
                        numberOfThreadsForLeftPart,
                        numberOfThreadsForRightPart
                    )
                }
                temporaryArray.merge(
                    Subarray(
                        0,
                        newMid
                    ),
                    Subarray(
                        newMid + 1,
                        numberOfElements - 1
                    ),
                    resultArray,
                    leftBoundOfSubarrayToPutElements,
                    numberOfThreads
                )
            }
        }
    }

    protected abstract fun IntArray.launchParallelSort(
        subarray: Subarray,
        mid: Int,
        newMid: Int,
        temporaryArray: IntArray,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int
    )
}

class MultiThreadMergeSorter : MergeSorter() {
    override val nameOfMethodUsed: String = "threads"

    override fun IntArray.launchParallelMerge(
        subarray1: Subarray,
        subarray2: Subarray,
        mid1: Int,
        mid2: Int,
        mid3: Int,
        resultArray: IntArray,
        leftBoundOfSubarrayToPutElements: Int,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int,
    ) {
        val firstThread =
            Thread {
                this.merge(
                    Subarray(subarray1.leftBound, mid1 - 1),
                    Subarray(subarray2.leftBound, mid2 - 1),
                    resultArray, leftBoundOfSubarrayToPutElements, numberOfThreadsForLeftPart
                )
            }
        val secondThread =
            Thread {
                this.merge(
                    Subarray(mid1 + 1, subarray1.rightBound),
                    Subarray(mid2, subarray2.rightBound),
                    resultArray, mid3 + 1, numberOfThreadsForRightPart
                )
            }
        firstThread.start()
        secondThread.start()
        firstThread.join()
        secondThread.join()
    }

    override fun IntArray.launchParallelSort(
        subarray: Subarray,
        mid: Int,
        newMid: Int,
        temporaryArray: IntArray,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int
    ) {
        val firstThread =
            Thread {
                this.mergeSortRecursive(
                    Subarray(subarray.leftBound, mid),
                    temporaryArray,
                    0,
                    numberOfThreadsForLeftPart
                )
            }
        val secondThread =
            Thread {
                this.mergeSortRecursive(
                    Subarray(mid + 1, subarray.rightBound),
                    temporaryArray,
                    newMid + 1,
                    numberOfThreadsForRightPart
                )
            }
        firstThread.start()
        secondThread.start()
        firstThread.join()
        secondThread.join()
    }
}

class AsyncMergeSorter : MergeSorter() {
    override val nameOfMethodUsed: String = "coroutines"

    override fun IntArray.launchParallelMerge(
        subarray1: Subarray,
        subarray2: Subarray,
        mid1: Int,
        mid2: Int,
        mid3: Int,
        resultArray: IntArray,
        leftBoundOfSubarrayToPutElements: Int,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int,
    ) {
        runBlocking {
            launch {
                this@launchParallelMerge.merge(
                    Subarray(subarray1.leftBound, mid1 - 1),
                    Subarray(subarray2.leftBound, mid2 - 1),
                    resultArray, leftBoundOfSubarrayToPutElements, numberOfThreadsForLeftPart
                )
            }
            launch {
                this@launchParallelMerge.merge(
                    Subarray(mid1 + 1, subarray1.rightBound),
                    Subarray(mid2, subarray2.rightBound),
                    resultArray, mid3 + 1, numberOfThreadsForRightPart
                )
            }
        }
    }

    override fun IntArray.launchParallelSort(
        subarray: Subarray,
        mid: Int,
        newMid: Int,
        temporaryArray: IntArray,
        numberOfThreadsForLeftPart: Int,
        numberOfThreadsForRightPart: Int
    ) {
        runBlocking {
            launch {
                this@launchParallelSort.mergeSortRecursive(
                    Subarray(subarray.leftBound, mid),
                    temporaryArray,
                    0,
                    numberOfThreadsForLeftPart
                )
            }
            launch {
                this@launchParallelSort.mergeSortRecursive(
                    Subarray(mid + 1, subarray.rightBound),
                    temporaryArray,
                    newMid + 1,
                    numberOfThreadsForRightPart
                )
            }
        }
    }
}
