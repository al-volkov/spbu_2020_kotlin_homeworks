package homework_1

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
    val number = try {
        input.toInt()
    } catch (e: NumberFormatException) {
        error("Error! The entered string cannot be interpreted as an integer.")
    }
    if (number < 0) {
        error("Error! A negative number has been entered.")
    }
    return number
}

fun main() {
    println("Enter a non-negative integer")
    val number = getUserInput()
    println("Factorial of $number, calculated iteratively, equals ${getIterativeFactorial(number)}")
    println("Factorial of $number, calculated recursively, equals ${getRecursiveFactorial(number)}")
}
