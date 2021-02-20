package homework1

object IterativeFactorial {
    fun get(n: Int): Int {
        var value = 1
        for (i in 2..n)
            value *= i
        return value
    }
}

object RecursiveFactorial {
    fun get(n: Int): Int {
        if (n == 0) return 1
        return n * get(n - 1)
    }
}

fun main() {
    println("Enter a non-negative integer")
    val number: Int = readLine()?.toInt() ?: 0
    println("Factorial of $number, calculated iteratively, equals ${IterativeFactorial.get(number)}")
    println("Factorial of $number, calculated recursively, equals ${RecursiveFactorial.get(number)}")
    return
}
