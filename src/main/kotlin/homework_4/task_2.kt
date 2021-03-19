package homework_4

import java.lang.IllegalArgumentException

const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4
const val NUMBER5 = 5
const val NUMBER6 = 6
const val NUMBER7 = 7
const val NUMBER8 = 8
const val NUMBER9 = 9

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
            NUMBER1 -> println(tree.size())
            NUMBER2 -> println(tree.isEmpty())
            NUMBER3 -> println(tree.containsKey(getKey()))
            NUMBER4 -> println(tree.containsValue(getValue()))
            NUMBER5 -> println(tree.get(getKey()))
            NUMBER6 -> tree.put(getKey(), getValue())
            NUMBER7 -> tree.remove(getKey())
            NUMBER8 -> tree.clear()
            NUMBER9 -> return
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
