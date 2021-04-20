package homework_6

fun IntArray.merge(left: Int, middle: Int, right: Int) {
    var it1 = 0
    var it2 = 0
    val temporaryArray = IntArray(right - left + 1) { 0 }
    while ((left + it1 <= middle) && (middle + 1 + it2 <= right)) {
        if (this[left + it1] < this[middle + 1 + it2]) {
            temporaryArray[it1 + it2] = this[left + it1]
            ++it1
        } else {
            temporaryArray[it1 + it2] = this[middle + 1 + it2]
            ++it2
        }
    }
    while (left + it1 <= middle) {
        temporaryArray[it1 + it2] = this[left + it1]
        ++it1
    }
    while (middle + 1 + it2 <= right) {
        temporaryArray[it1 + it2] = this[middle + 1 + it2]
        ++it2
    }
    for (i in temporaryArray.indices) {
        this[left + i] = temporaryArray[i]
    }
}

/**
 * single-threaded
 */
fun IntArray.mergeSort(left: Int = 0, right: Int = this.lastIndex) {
    if (left >= right) {
        return
    }
    val middle = (left + right) / 2
    this.mergeSort(left, middle)
    this.mergeSort(middle + 1, right)
    this.merge(left, middle, right)
}

/**
 * multi-threaded
 */
fun IntArray.mergeSortMT(left: Int = 0, right: Int = this.lastIndex, numberOfThreads: Int = 0) {
    if (left >= right) {
        return
    }
    if (numberOfThreads <= 1) {
        this.mergeSort(left, right)
    } else {
        val middle = (left + right) / 2
        val numberOfThreadsForLeftPart = numberOfThreads / 2
        val numberOfThreadsForRightPart = numberOfThreads - numberOfThreadsForLeftPart
        val firstThread = Thread { this.mergeSortMT(left, middle, numberOfThreadsForLeftPart) }
        val secondThread = Thread { this.mergeSortMT(middle + 1, right, numberOfThreadsForRightPart) }
        firstThread.start()
        secondThread.start()
        firstThread.join()
        secondThread.join()
        this.merge(left, middle, right)
    }
}
