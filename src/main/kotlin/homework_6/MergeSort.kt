@file:Suppress("LongParameterList") // for fun mergeMT

package homework_6

object MergeSort {
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

    /**
     * merges two subarrays into [resultArray]
     * works multithreaded
     * [leftBound1] start of the first subarray
     * [rightBound1] end of the first subarray
     * [leftBound2] start of the second subarray
     * [rightBound2] end of the second subarray
     * [resultArray] an array where the elements will be placed
     * [leftBound3] the index from which the elements will be placed in [resultArray]
     * [numberOfThreads] number of threads
     */
    private fun IntArray.mergeMT(
        leftBound1: Int,
        rightBound1: Int,
        leftBound2: Int,
        rightBound2: Int,
        resultArray: IntArray,
        leftBound3: Int,
        numberOfThreads: Int = 1
    ) {
        val numberOfElementsInFirstSubarray = rightBound1 - leftBound1 + 1
        val numberOfElementsInSecondSubarray = rightBound2 - leftBound2 + 1
        if (numberOfElementsInFirstSubarray < numberOfElementsInSecondSubarray) {
            this.mergeMT(leftBound2, rightBound2, leftBound1, rightBound1, resultArray, leftBound3)
            return
        }
        if (numberOfElementsInFirstSubarray == 0) {
            return
        } else {
            val mid1 = (leftBound1 + rightBound1) / 2 // middle of the first subarray
            val mid2 = this.binarySearch(this[mid1], leftBound2, rightBound2) // see binarySearch comments
            val mid3 = leftBound3 + (mid1 - leftBound1) + (mid2 - leftBound2) // index in resultArray, see next line
            resultArray[mid3] = this[mid1]
            if (numberOfThreads <= 1) {
                this.mergeMT(leftBound1, mid1 - 1, leftBound2, mid2 - 1, resultArray, leftBound3)
                this.mergeMT(mid1 + 1, rightBound1, mid2, rightBound2, resultArray, mid3 + 1)
            } else {
                val numberOfThreadsForLeftPart = numberOfThreads / 2
                val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                val firstThread =
                    Thread {
                        this.mergeMT(
                            leftBound1,
                            mid1 - 1,
                            leftBound2,
                            mid2 - 1,
                            resultArray,
                            leftBound3,
                            numberOfThreadsForLeftPart
                        )
                    }
                val secondThread =
                    Thread {
                        this.mergeMT(
                            mid1 + 1,
                            rightBound1,
                            mid2,
                            rightBound2,
                            resultArray,
                            mid3 + 1,
                            numberOfThreadsForRightPart
                        )
                    }
                firstThread.start()
                secondThread.start()
                firstThread.join()
                secondThread.join()
            }
        }
    }

    /**
     * sorts the array (or subarray) and puts the result in another array
     * works multithreaded
     * [leftBound1] start of the first subarray
     * [rightBound1] end of the first subarray
     * [resultArray] an array where the elements will be placed
     * [leftBound2] the index from which the elements will be placed
     * [numberOfThreads] number of threads
     */
    private fun IntArray.mergeSortMTRecursive(
        leftBound1: Int = 0,
        rightBound1: Int = lastIndex,
        resultArray: IntArray,
        leftBound2: Int = 0,
        numberOfThreads: Int = 1
    ) {
        when (val numberOfElements = rightBound1 - leftBound1 + 1) {
            0 -> return
            1 -> {
                resultArray[leftBound2] = this[leftBound1]
            }
            else -> {
                val temporaryArray = IntArray(numberOfElements) { 0 }
                val mid = (leftBound1 + rightBound1) / 2
                val newMid = mid - leftBound1
                if (numberOfThreads <= 1) {
                    this.mergeSortMTRecursive(leftBound1, mid, temporaryArray, 0)
                    this.mergeSortMTRecursive(mid + 1, rightBound1, temporaryArray, newMid + 1)
                } else {
                    val numberOfThreadsForLeftPart = numberOfThreads / 2
                    val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                    val firstThread =
                        Thread {
                            this.mergeSortMTRecursive(
                                leftBound1,
                                mid,
                                temporaryArray,
                                0,
                                numberOfThreadsForLeftPart
                            )
                        }
                    val secondThread =
                        Thread {
                            this.mergeSortMTRecursive(
                                mid + 1,
                                rightBound1,
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
                temporaryArray.mergeMT(
                    0,
                    newMid,
                    newMid + 1,
                    numberOfElements - 1,
                    resultArray,
                    leftBound2,
                    numberOfThreads
                )
            }
        }
    }

    /**
     * Calls the recursive function
     * */
    fun IntArray.mergeSortMT(numberOfThreads: Int) {
        val temporaryArray = IntArray(this.size) { 0 }
        this.mergeSortMTRecursive(resultArray = temporaryArray, numberOfThreads = numberOfThreads)
        temporaryArray.copyInto(this)
    }
}
