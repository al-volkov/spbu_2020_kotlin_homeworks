package homework_6

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AsyncMergeSorter : MergeSorter() {
    override val nameOfMethodUsed: String = "coroutines"

    override fun launchParallel(runOnLeft: Runnable, runOnRight: Runnable) {
        runBlocking {
            launch { runOnLeft.run() }
            launch { runOnRight.run() }
        }
    }
}
