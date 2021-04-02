package utils

fun getKey(): String {
    val scan = java.util.Scanner(System.`in`)
    println("enter key")
    return scan.nextLine()
}

fun getValue(): Double {
    val scan = java.util.Scanner(System.`in`)
    println("enter value")
    return scan.nextDouble()
}
