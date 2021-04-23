package homework_6

import java.lang.IllegalArgumentException

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    println("enter the maximum number of elements")
    val maxNumberOfElements = scanner.nextInt()
    println("enter the step with which the measurements will be taken")
    val step = scanner.nextInt()
    println("enter the numbers of threads for which the time will be measured (in one line)")
    val numbersOfThreadsString = readLine()
    if (numbersOfThreadsString != null) {
        val numbersOfThreads = numbersOfThreadsString.split(" ").map { it.toInt() }
        DrawPlot(numbersOfThreads, maxNumberOfElements, step)
    } else {
        throw IllegalArgumentException("entered string cannot be interpreted as List<Int>")
    }
}
