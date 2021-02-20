package firsthomework

val scanner = java.util.Scanner(System.`in`)

fun numberOfOccurrences(string1: String, string2: String): Int {
    var count = 0
    for (i in 0..(string1.length - string2.length)) {
        if (string1[i] == string2[0]) {
            if (string1.substring(i, i + string2.length) == string2) {
                ++count
            }
        }
    }
    return count
}

fun main() {
    println("Enter first string")
    val string1 = scanner.nextLine()
    println("Enter second string")
    val string2 = scanner.nextLine()
    println("The second line is " +
            "${if (string1.length >= string2.length) numberOfOccurrences(string1,string2) else 0}" +
            " times included in the first as a substring")
    return
}
