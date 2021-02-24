package firsthomework

fun getIterativeFactorial(n: Int): Int {
    var value = 1
    for (i in 2..n) {
        value *= i
    }
    return value
}

fun getRecursiveFactorial(n: Int): Int {
    if (n == 0) {
        return 1
    }
    return n * getRecursiveFactorial(n - 1)
}

fun main() {
    println("Enter a non-negative integer")
    var number: Int
    val input: String = java.util.Scanner(System.`in`).next()
    try {
        number = input.toInt()
    } catch (e: NumberFormatException) {
        println("Error! The entered string cannot be interpreted as an integer")
        return
    }
    if (number < 0) {
        println("Error! A negative number has been entered.")
        return
    }
    println("Factorial of $number, calculated iteratively, equals ${getIterativeFactorial(number)}")
    println("Factorial of $number, calculated recursively, equals ${getRecursiveFactorial(number)}")
}
