@file:Suppress("MagicNumber")

package homework_4

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

fun AVLTree<Int, String>.doAction(numberOfAction: Int): Boolean {
    when (numberOfAction) {
        1 -> println(this.size)
        2 -> println(this.isEmpty())
        3 -> println(this.containsKey(getKey()))
        4 -> println(this.containsValue(getValue()))
        5 -> println(this[getKey()])
        6 -> this.put(getKey(), getValue())
        7 -> this.remove(getKey())
        8 -> {
            for (i in this.entries) {
                print("(${i.key},${i.value}) ")
            }
            println()
        }
        9 -> println(this.keys)
        10 -> println(this.values)
        else -> return false
    }
    return true
}

fun interfaceLoop() {
    val tree = AVLTree<Int, String>() // int and string are selected for example
    val scan = java.util.Scanner(System.`in`)
    println("\nenter the number of the next operation")
    var enteredNumber = scan.nextInt()
    while (tree.doAction(enteredNumber)) {
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
                "enter 8 to print entries\n" +
                "enter 9 to print keys\n" +
                "enter 10 to print values\n" +
                "enter anything else to exit"
    )
    interfaceLoop()
}


fun main() {
    usersInterface()
}
