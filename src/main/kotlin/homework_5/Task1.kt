package homework_5

import java.io.File

fun String.isNumber() = this.toIntOrNull() != null

enum class OperationType(val operation: String) {
    Addition("+"),
    Multiplication("*"),
    Subtraction("-"),
    Division("/")
}

interface ArithmeticTreeNode {
    fun getValue(): Int
    override fun toString(): String
}

class Operand(private val value: Int) : ArithmeticTreeNode {
    override fun getValue() = value
    override fun toString() = value.toString()
}

class Operation(
    private val type: OperationType,
    private val leftNode: ArithmeticTreeNode,
    private val rightNode: ArithmeticTreeNode
) : ArithmeticTreeNode {
    override fun getValue(): Int {
        return when (type) {
            OperationType.Addition -> leftNode.getValue() + rightNode.getValue()
            OperationType.Multiplication -> leftNode.getValue() * rightNode.getValue()
            OperationType.Subtraction -> leftNode.getValue() - rightNode.getValue()
            OperationType.Division -> leftNode.getValue() / rightNode.getValue()
        }
    }

    override fun toString(): String = "(${type.operation} $leftNode $rightNode)"
}

class ArithmeticTree(path: String) {
    private val root: ArithmeticTreeNode

    init {
        val input = File(path).readText().replace("(", "").replace(")", "").split(" ")
        root = parseRecursive(input).node
    }

    data class RecursiveResult(val node: ArithmeticTreeNode, val newList: List<String>)

    private fun parseRecursive(list: List<String>): RecursiveResult {
        if (list.first().isNumber()) {
            return RecursiveResult(Operand(list.first().toInt()), list.slice(1..list.lastIndex))
        } else {
            val type = when (list.first()) {
                "+" -> OperationType.Addition
                "*" -> OperationType.Multiplication
                "-" -> OperationType.Subtraction
                "/" -> OperationType.Division
                else -> throw IllegalArgumentException("arithmetic expression is not correct")
            }
            var result = parseRecursive(list.slice(1..list.lastIndex))
            val leftNode = result.node
            result = parseRecursive(result.newList)
            val rightNode = result.node
            return RecursiveResult(Operation(type, leftNode, rightNode), result.newList)
        }
    }

    override fun toString() = root.toString()
    fun getValue() = root.getValue()
}
