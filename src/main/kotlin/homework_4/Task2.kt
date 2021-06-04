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

fun AVLTree<Int, String>.doAction(nameOfAction: String): Boolean {
    when (nameOfAction) {
        "size" -> println(this.size)
        "isEmpty" -> println(this.isEmpty())
        "containsKey" -> println(this.containsKey(getKey()))
        "containsValue" -> println(this.containsValue(getValue()))
        "get" -> println(this[getKey()])
        "put" -> this.put(getKey(), getValue())
        "remove" -> this.remove(getKey())
        "entries" -> {
            for (i in this.entries) {
                print("(${i.key},${i.value}) ")
            }
            println()
        }
        "keys" -> println(this.keys)
        "values" -> println(this.values)
        else -> return false
    }
    return true
}

fun interfaceLoop() {
    val tree = AVLTree<Int, String>() // int and string are selected for example
    val scan = java.util.Scanner(System.`in`)
    println("\nenter the name of the next function")
    var enteredName = scan.next()
    while (tree.doAction(enteredName)) {
        println("enter the name of the next function")
        enteredName = scan.next()
    }
}

fun usersInterface() {
    println(
        "enter the name of function to use it. Names:\n" +
                "size\n" +
                "isEmpty\n" +
                "containsKey\n" +
                "containsValue\n" +
                "get\n" +
                "put\n" +
                "remove\n" +
                "entries\n" +
                "keys\n" +
                "values\n" +
                "enter anything else to exit"
    )
    interfaceLoop()
}

fun main() {
    usersInterface()
}
