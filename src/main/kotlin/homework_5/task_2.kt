package homework_5

fun interfaceLoop(table: HashTable<String, Double>) {
    while (true) {
        println("enter the name of next action")
        when (java.util.Scanner(System.`in`).nextLine()) {
            Add().name -> Add().execute(table)
            Remove().name -> Remove().execute(table)
            Get().name -> Get().execute(table)
            Contains().name -> Contains().execute(table)
            GetStatistics().name -> GetStatistics().execute(table)
            FillFromFile().name -> {
                FillFromFile().execute(table)
            }
            ChangeHashFunction().name -> ChangeHashFunction().execute(table)
            else -> break
        }
    }
}

fun usersInterface() {
    println(
        "enter action name to use it. Names\n" +
                "add\n" +
                "remove\n" +
                "get\n" +
                "contains\n" +
                "get statistics\n" +
                "read from file\n" +
                "change hash function\n" +
                "enter anything else to exit\n"
    )
    val table = HashTable<String, Double>(DefaultHashFunction1())
    interfaceLoop(table)
}

fun main() {
    usersInterface()
}
