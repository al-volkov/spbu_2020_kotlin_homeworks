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
 * dual-threaded
 */
fun IntArray.mergeSortMt2(left: Int = 0, right: Int = this.lastIndex) {
    if (left >= right) {
        return
    }
    val middle = (left + right) / 2
    val firstThread = Thread { this.mergeSort(left, middle) }
    val secondThread = Thread { this.mergeSort(middle + 1, right) }
    firstThread.start()
    secondThread.start()
    firstThread.join()
    secondThread.join()
    this.merge(left, middle, right)
}

/**
 * four-threaded
 */
fun IntArray.mergeSortMt4(left: Int = 0, right: Int = this.lastIndex) {
    if (left >= right) {
        return
    }
    val middle2 = (left + right) / 2
    val middle1 = (left + middle2) / 2
    val middle3 = (middle2 + right) / 2
    val listOfThreads = mutableListOf<Thread>()
    listOfThreads.add(Thread { this.mergeSort(left, middle1) })
    listOfThreads.add(Thread { this.mergeSort(middle1 + 1, middle2) })
    listOfThreads.add(Thread { this.mergeSort(middle2 + 1, middle3) })
    listOfThreads.add(Thread { this.mergeSort(middle3 + 1, right) })
    listOfThreads.forEach { it.start() }
    listOfThreads.forEach { it.join() }
    val thread1 = Thread { this.merge(left, middle1, middle2) }
    val thread2 = Thread { this.merge(middle2 + 1, middle3, right) }
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    this.merge(left, middle2, right)
}

/**
 *multithreaded without limit on the number of threads
 */
fun IntArray.mergeSortMt(left: Int = 0, right: Int = this.lastIndex) {
    if (left >= right) {
        return
    }
    val middle = (left + right) / 2
    val firstThread = Thread { this.mergeSortMt(left, middle) }
    val secondThread = Thread { this.mergeSortMt(middle + 1, right) }
    firstThread.start()
    secondThread.start()
    firstThread.join()
    secondThread.join()
    this.merge(left, middle, right)
}
