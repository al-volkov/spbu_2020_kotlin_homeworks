@file:Suppress("LongParameterList") // for fun mergeMT

package homework_6

fun IntArray.binarySearch(value: Int, left: Int, right: Int): Int {
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
 * multi-thread merge
 */
fun IntArray.mergeMT(
    left1: Int,
    right1: Int,
    left2: Int,
    right2: Int,
    resultArray: IntArray,
    left3: Int,
    numberOfThreads: Int = 1
) {
    val n1 = right1 - left1 + 1
    val n2 = right2 - left2 + 1
    if (n1 < n2) {
        this.mergeMT(left2, right2, left1, right1, resultArray, left3)
        return
    }
    if (n1 == 0) {
        return
    } else {
        val mid1 = (left1 + right1) / 2
        val mid2 = this.binarySearch(this[mid1], left2, right2)
        val mid3 = left3 + (mid1 - left1) + (mid2 - left2)
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
 * multi-threaded with multi thread merge
 */
fun IntArray.mergeSortMT(
    left1: Int = 0,
    right1: Int = lastIndex,
    resultArray: IntArray,
    left2: Int = 0,
    numberOfThreads: Int = 1
) {
    when (val n = right1 - left1 + 1) {
        0 -> return
        1 -> {
            resultArray[left2] = this[left1]
        }
        else -> {
            val temporaryArray = IntArray(n) { 0 }
            val mid = (left1 + right1) / 2
            val newMid = mid - left1
            if (numberOfThreads <= 1) {
                this.mergeSortMT(left1, mid, temporaryArray, 0)
                this.mergeSortMT(mid + 1, right1, temporaryArray, newMid + 1)
            } else {
                val numberOfThreadsForLeftPart = numberOfThreads / 2
                val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
                val firstThread =
                    Thread { this.mergeSortMT(left1, mid, temporaryArray, 0, numberOfThreadsForLeftPart) }
                val secondThread =
                    Thread {
                        this.mergeSortMT(
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
            temporaryArray.mergeMT(0, newMid, newMid + 1, n - 1, resultArray, left2, numberOfThreads)
        }
    }
}
