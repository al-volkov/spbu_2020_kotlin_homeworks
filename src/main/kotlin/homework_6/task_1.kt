package homework_6

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    println("enter the number of threads")
    val numberOfThreads = scanner.nextInt()
    println("enter the maximum number of elements")
    val maxNumberOfElements = scanner.nextInt()
    println("enter the step with which the measurements will be taken")
    val step = scanner.nextInt()
    DrawPlot(numberOfThreads, maxNumberOfElements, step)
}
