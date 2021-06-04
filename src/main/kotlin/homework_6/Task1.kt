package homework_6

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    println("enter the maximum number of elements")
    val maxNumberOfElements = scanner.nextInt()
    println("enter the step with which the measurements will be taken")
    val step = scanner.nextInt()
    println("enter 1 if you want to use multithreaded sort and 2 if you want to use asynchronous sort")
    val sorter = when (scanner.nextInt()) {
        1 -> MultiThreadMergeSorter
        2 -> AsyncMergeSorter
        else -> throw IllegalArgumentException("only 1 or 2 can be entered")
    }
    println("enter the numbers of threads/coroutines for which the time will be measured (in one line)")
    val numbersOfThreadsString = readLine()
    if (numbersOfThreadsString != null) {
        val numbersOfThreads = numbersOfThreadsString.split(" ").map { it.toInt() }
        DrawPlot(sorter, numbersOfThreads, maxNumberOfElements, step)
    } else {
        throw IllegalArgumentException("entered string cannot be interpreted as List<Int>")
    }
}
