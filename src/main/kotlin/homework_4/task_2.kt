package homework_4

import java.lang.IllegalArgumentException

fun getKey(): Int {
    val scan = java.util.Scanner(System.`in`)
    println("enter key")
    return scan.nextInt()
}

fun getValue(): String {
    val scan = java.util.Scanner(System.`in`)
    println("enter value")
    return scan.nextLine()
}

fun interfaceLoop() {
    val tree = AVLTree<Int, String>() // int and string are selected for example
    val scan = java.util.Scanner(System.`in`)
    println("enter the number of the next operation")
    var enteredNumber = scan.nextInt()
    while (true) {
        when (enteredNumber) {
            1 -> println(tree.size())
            2 -> println(tree.isEmpty())
            3 -> println(tree.containsKey(getKey()))
            4 -> println(tree.containsValue(getValue()))
            5 -> println(tree.get(getKey()))
            6 -> tree.put(getKey(), getValue())
            7 -> tree.remove(getKey())
            8 -> tree.clear()
            9 -> return
            else -> throw IllegalArgumentException()
        }
        println("enter the number of the next operation")
        enteredNumber = scan.nextInt()
    }
}

fun usersInterface() {
    println(
        "enter 1 to use size()\n" +
                "enter 2 to use isEmpty()\n" +
                "enter 3 to use containsKey()\n" +
                "enter 4 to use containsValue()\n" +
                "enter 5 to use get()\n" +
                "enter 6 to use put()\n" +
                "enter 7 to use remove()\n" +
                "enter 8 to use clear()\n" +
                "enter 9 ot exit"
    )
    interfaceLoop()
}

fun main() {
    usersInterface()
}
