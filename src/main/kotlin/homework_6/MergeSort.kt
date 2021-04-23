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
     * [left1] start of the first subarray
     * [right1] end of the first subarray
     * [left2] start of the second subarray
     * [right2] end of the second subarray
     * [resultArray] an array where the elements will be placed
     * [left3] the index from which the elements will be placed
     * [numberOfThreads] number of threads
     */
    private fun IntArray.mergeMT(
        left1: Int,
        right1: Int,
        left2: Int,
        right2: Int,
        resultArray: IntArray,
        left3: Int,
        numberOfThreads: Int = 1
    ) {
        val numberOfElementsInFirstSubarray = right1 - left1 + 1
        val numberOfElementsInSecondSubarray = right2 - left2 + 1
        if (numberOfElementsInFirstSubarray < numberOfElementsInSecondSubarray) {
            this.mergeMT(left2, right2, left1, right1, resultArray, left3)
            return
        }
        if (numberOfElementsInFirstSubarray == 0) {
            return
        } else {
            val mid1 = (left1 + right1) / 2 // middle of the first subarray
            val mid2 = this.binarySearch(this[mid1], left2, right2) // see binarySearch comments
            val mid3 = left3 + (mid1 - left1) + (mid2 - left2) // index in resultArray, see next line
            resultArray[mid3] = this[mid1]
            if (numberOfThreads <= 1) {
                this.mergeMT(left1, mid1 - 1, left2, mid2 - 1, resultArray, left3)
                this.mergeMT(mid1 + 1, right1, mid2, right2, resultArray, mid3 + 1)
            } else {
                val numberOfThreadsForLeftPart = numberOfThreads / 2
                val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                val firstThread =
                    Thread {
                        this.mergeMT(
                            left1,
                            mid1 - 1,
                            left2,
                            mid2 - 1,
                            resultArray,
                            left3,
                            numberOfThreadsForLeftPart
                        )
                    }
                val secondThread =
                    Thread {
                        this.mergeMT(
                            mid1 + 1,
                            right1,
                            mid2,
                            right2,
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
     * [left1] start of the first subarray
     * [right1] end of the first subarray
     * [resultArray] an array where the elements will be placed
     * [left2] the index from which the elements will be placed
     * [numberOfThreads] number of threads
     */
    private fun IntArray.mergeSortMTRecursive(
        left1: Int = 0,
        right1: Int = lastIndex,
        resultArray: IntArray,
        left2: Int = 0,
        numberOfThreads: Int = 1
    ) {
        when (val numberOfElements = right1 - left1 + 1) {
            0 -> return
            1 -> {
                resultArray[left2] = this[left1]
            }
            else -> {
                val temporaryArray = IntArray(numberOfElements) { 0 }
                val mid = (left1 + right1) / 2
                val newMid = mid - left1
                if (numberOfThreads <= 1) {
                    this.mergeSortMTRecursive(left1, mid, temporaryArray, 0)
                    this.mergeSortMTRecursive(mid + 1, right1, temporaryArray, newMid + 1)
                } else {
                    val numberOfThreadsForLeftPart = numberOfThreads / 2
                    val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                    val firstThread =
                        Thread { this.mergeSortMTRecursive(left1, mid, temporaryArray, 0, numberOfThreadsForLeftPart) }
                    val secondThread =
                        Thread {
                            this.mergeSortMTRecursive(
                                mid + 1,
                                right1,
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
                temporaryArray.mergeMT(0, newMid, newMid + 1, numberOfElements - 1, resultArray, left2, numberOfThreads)
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
