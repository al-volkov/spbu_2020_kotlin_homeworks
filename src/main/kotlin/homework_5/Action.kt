package homework_5

import java.io.File
import java.lang.IllegalArgumentException

interface Action {
    val name: String
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
    override val name: String
        get() = "add"

    override fun execute(table: HashTable<String, Double>) {
        table.add(getKey(), getValue())
    }
}

class Remove : Action {
    override val name: String
        get() = "remove"

    override fun execute(table: HashTable<String, Double>) {
        table.remove(getKey())
    }
}

class Get : Action {
    override val name: String
        get() = "get"

    override fun execute(table: HashTable<String, Double>) {
        println(table[getKey()])
    }
}

class Contains : Action {
    override val name: String
        get() = "contains"

    override fun execute(table: HashTable<String, Double>) {
        println(table.contains(getKey()))
    }
}

class GetStatistics : Action {
    override val name: String
        get() = "get statistics"

    override fun execute(table: HashTable<String, Double>) {
        print(table.getStatistics())
    }
}

class FillFromFile : Action {
    override val name: String
        get() = "fill from file"

    override fun execute(table: HashTable<String, Double>) {
        println("enter file name")
        val fileName = java.util.Scanner(System.`in`).next()
        val input = File(FillFromFile::class.java.getResource(fileName).path).readText()
        val pairs = input.changeFormat()
        pairs.forEach {
            val pair = it.split(",")
            table.add(pair.first(), pair.last().toDouble())
        }
    }
}

class ChangeHashFunction : Action {
    override val name: String
        get() = "change hash function"

    override fun execute(table: HashTable<String, Double>) {
        println("enter the number of function, which you want to use")
        when (java.util.Scanner(System.`in`).next()) {
            "1" -> table.changeHashFunction(DefaultHashFunction1())
            "2" -> table.changeHashFunction(DefaultHashFunction2())
            "3" -> table.changeHashFunction(DefaultHashFunction3())
            else -> throw IllegalArgumentException("you must enter the number in range 1..3")
        }
    }
}
