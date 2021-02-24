package firsthomework

fun String.numberOfOccurrences(substr: String): Int {
    if ((this.length < substr.length) || (kotlin.math.min(this.length, substr.length) == 0)) {
        return 0
    }
    var count = 0
    for (i in this.windowed(substr.length)) {
        if (i == substr) {
            ++count
        }
    }
    return count
}

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    println("Enter first string")
    val string1 = scanner.nextLine()
    println("Enter second string")
    val string2 = scanner.nextLine()
    val output = "The second line is " +
            "${string1.numberOfOccurrences(string2)}" +
            " times included in the first as a substring"
    println(output)
}
