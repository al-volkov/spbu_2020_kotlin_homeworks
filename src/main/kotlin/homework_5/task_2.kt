package homework_5

import java.io.File
import java.lang.IllegalArgumentException

interface Action {
    fun execute(table: HashTable<String, Double>)
}

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

fun String.changeFormat(): List<String> = this.replace("(", "").replace(")", "").split(", ")

class Add : Action {
    override fun execute(table: HashTable<String, Double>) {
        table.add(getKey(), getValue())
    }
}

class Remove : Action {
    override fun execute(table: HashTable<String, Double>) {
        table.remove(getKey())
    }
}

class Get : Action {
    override fun execute(table: HashTable<String, Double>) {
        println(table.get(getKey()))
    }
}

class Contains : Action {
    override fun execute(table: HashTable<String, Double>) {
        println(table.contains(getKey()))
    }
}

class GetStatistics : Action {
    override fun execute(table: HashTable<String, Double>) {
        print(table.getStatistics())
    }
}

class FillFromFile(private val fileName: String) : Action {
    override fun execute(table: HashTable<String, Double>) {
        val input = File(FillFromFile::class.java.getResource(fileName).path).readText()
        val pairs = input.changeFormat()
        pairs.forEach {
            val pair = it.split(",")
            table.add(pair.first(), pair.last().toDouble())
        }
    }
}

class ChangeHashFunction : Action {
    override fun execute(table: HashTable<String, Double>) {
        println("enter the number of function, which you want to use")
        when (java.util.Scanner(System.`in`).next()) {
            "1" -> table.changeHashFunction(HashTable.DefaultHashFunction1())
            "2" -> table.changeHashFunction(HashTable.DefaultHashFunction2())
            "3" -> table.changeHashFunction(HashTable.DefaultHashFunction3())
            else -> throw IllegalArgumentException("you must enter the number in range 1..3")
        }
    }
}

fun interfaceLoop(table: HashTable<String, Double>) {
    while (true) {
        println("enter the number of next action")
        when (java.util.Scanner(System.`in`).next()) {
            "1" -> Add().execute(table)
            "2" -> Remove().execute(table)
            "3" -> Get().execute(table)
            "4" -> Contains().execute(table)
            "5" -> GetStatistics().execute(table)
            "6" -> {
                println("enter file name")
                FillFromFile(java.util.Scanner(System.`in`).next()).execute(table)
            }
            "7" -> ChangeHashFunction().execute(table)
            else -> break
        }
    }
}

fun usersInterface() {
    println(
        "enter 1 to add element\n" +
                "enter 2 to remove element\n" +
                "enter 3 to get element\n" +
                "enter 4 to check if element contained in table\n" +
                "enter 5 to view statistics\n" +
                "enter 6 to fill table from file\n" +
                "enter 7 to change hash function\n" +
                "enter anything else to exit\n"
    )
    val table = HashTable<String, Double>(HashTable.DefaultHashFunction1())
    interfaceLoop(table)
}

fun main() {
    usersInterface()
}
