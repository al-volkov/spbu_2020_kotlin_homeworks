package homework_5

fun interfaceLoop(table: HashTable<String, Double>) {
    while (true) {
        println("enter the name of next action")
        val name = java.util.Scanner(System.`in`).nextLine()
        var isActionExecuted = false
        actions.forEach {
            if (name == it.name) {
                it.execute(table)
                isActionExecuted = true
            }
        }
        if (!isActionExecuted) {
            break
        }
    }
}

fun usersInterface() {
    println("enter action name to use it. Names:")
    actions.forEach { println(it.name) }
    println("enter anything else to exit")
    val table = HashTable<String, Double>(DefaultHashFunction1())
    interfaceLoop(table)
}

fun main() {
    usersInterface()
}
