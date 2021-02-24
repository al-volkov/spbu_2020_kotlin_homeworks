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

fun getUserInput(): Int {
    val input: String = java.util.Scanner(System.`in`).next()
    return (try {
        input.toInt()
    } catch (e: NumberFormatException) {
        error("Error! The entered string cannot be interpreted as an integer.")
    })
}

fun main() {
    println("Enter a non-negative integer")
    val number = getUserInput()
    if (number < 0) {
        error("Error! A negative number has been entered.")
    }
    println("Factorial of $number, calculated iteratively, equals ${getIterativeFactorial(number)}")
    println("Factorial of $number, calculated recursively, equals ${getRecursiveFactorial(number)}")
}
