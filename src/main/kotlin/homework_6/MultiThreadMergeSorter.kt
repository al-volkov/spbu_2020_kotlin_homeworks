package homework_6

class MultiThreadMergeSorter : MergeSorter() {
    override val nameOfMethodUsed: String = "threads"

    override fun launchParallel(runOnLeft: Runnable, runOnRight: Runnable) {
        val firstThread = Thread { runOnLeft.run() }
        val secondThread = Thread { runOnRight.run() }
        firstThread.start()
        secondThread.start()
        firstThread.join()
        secondThread.join()
    }
}
