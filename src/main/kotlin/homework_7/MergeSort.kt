@file:Suppress("LongParameterList") // for fun asyncMerge

package homework_7

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
suspend fun IntArray.asyncMerge(
    left1: Int,
    right1: Int,
    left2: Int,
    right2: Int,
    resultArray: IntArray,
    left3: Int,
    numberOfCoroutines: Int = 1
) {
    val n1 = right1 - left1 + 1
    val n2 = right2 - left2 + 1
    if (n1 < n2) {
        this.asyncMerge(left2, right2, left1, right1, resultArray, left3)
        return
    }
    if (n1 == 0) {
        return
    } else {
        val mid1 = (left1 + right1) / 2
        val mid2 = this.binarySearch(this[mid1], left2, right2)
        val mid3 = left3 + (mid1 - left1) + (mid2 - left2)
        resultArray[mid3] = this[mid1]
        if (numberOfCoroutines <= 1) {
            this.asyncMerge(left1, mid1 - 1, left2, mid2 - 1, resultArray, left3)
            this.asyncMerge(mid1 + 1, right1, mid2, right2, resultArray, mid3 + 1)
        } else {
            val numberOfCoroutinesForLeftPart = numberOfCoroutines / 2
            val numberOfCoroutinesForRightPart = numberOfCoroutines - numberOfCoroutinesForLeftPart
            val array = this
            coroutineScope {
                val firstCoroutine = launch {
                    array.asyncMerge(
                        left1,
                        mid1 - 1,
                        left2,
                        mid2 - 1,
                        resultArray,
                        left3,
                        numberOfCoroutinesForLeftPart
                    )
                }
                val secondCoroutine = launch {
                    array.asyncMerge(
                        mid1 + 1,
                        right1,
                        mid2,
                        right2,
                        resultArray,
                        mid3 + 1,
                        numberOfCoroutinesForRightPart
                    )
                }
                firstCoroutine.join()
                secondCoroutine.join()
            }
        }
    }
}

/**
 * multi-threaded with multi thread merge
 */
suspend fun IntArray.asyncMergeSort(
    left1: Int = 0,
    right1: Int = lastIndex,
    resultArray: IntArray,
    left2: Int = 0,
    numberOfCoroutines: Int = 1
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
            if (numberOfCoroutines <= 1) {
                this.asyncMergeSort(left1, mid, temporaryArray, 0)
                this.asyncMergeSort(mid + 1, right1, temporaryArray, newMid + 1)
            } else {
                val numberOfCoroutinesForLeftPart = numberOfCoroutines / 2
                val numberOfCoroutinesForRightPart = numberOfCoroutines - numberOfCoroutinesForLeftPart
                val array = this
                coroutineScope {
                    val firstCoroutine =
                        launch { array.asyncMergeSort(left1, mid, temporaryArray, 0, numberOfCoroutinesForLeftPart) }
                    val secondCoroutine = launch {
                        array.asyncMergeSort(
                            mid + 1,
                            right1,
                            temporaryArray,
                            newMid + 1,
                            numberOfCoroutinesForRightPart
                        )
                    }
                    firstCoroutine.join()
                    secondCoroutine.join()
                }
            }
            temporaryArray.asyncMerge(0, newMid, newMid + 1, n - 1, resultArray, left2, numberOfCoroutines)
        }
    }
}
