package firsthomework

fun getIterativeFactorial(n: Int): Int {
    var value = 1
    for (i in 2..n)
        value *= i
    return value
}

fun getRecursiveFactorial(n: Int): Int {
    if (n == 0) return 1
    return n * getRecursiveFactorial(n - 1)
}

fun main() {
    println("Enter a non-negative integer")
    val number: Int = readLine()?.toInt() ?: 0
    println("Factorial of $number, calculated iteratively, equals ${getIterativeFactorial(number)}")
    println("Factorial of $number, calculated recursively, equals ${getRecursiveFactorial(number)}")
    return
}
