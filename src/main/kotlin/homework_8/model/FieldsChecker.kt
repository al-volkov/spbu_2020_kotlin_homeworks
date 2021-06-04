package homework_8.model

private const val SIZE = 3
private val firstPlayerWon = List(SIZE) { Player.FIRST.symbol }.joinToString("")
private val secondPlayerWon = List(SIZE) { Player.SECOND.symbol }.joinToString("")

fun Array<CharArray>.getWinner(): Player? {
    return when (this.checkRows() + this.checkColumns() + this.checkDiagonal()) {
        1 -> Player.FIRST
        2 -> Player.SECOND
        else -> null
    }
}

private fun Array<CharArray>.checkRows(): Int {
    var result = 0
    for (row in this) {
        if (row.joinToString("") == firstPlayerWon) result = 1
        if (row.joinToString("") == secondPlayerWon) result = 2
    }
    return result
}

private fun Array<CharArray>.checkColumns(): Int {
    var result = 0
    for (column in 0 until SIZE) {
        var string = ""
        for (row in 0 until SIZE) {
            string += this[row][column]
        }
        if (string == firstPlayerWon) result = 1
        if (string == secondPlayerWon) result = 2
    }
    return result
}

private fun Array<CharArray>.checkDiagonal(): Int {
    var result = 0
    var string = ""
    for (i in 0 until SIZE) {
        string += this[i][i]
    }
    if (string == firstPlayerWon) result = 1
    if (string == secondPlayerWon) result = 2
    string = ""
    for (i in 0 until SIZE) {
        string += this[SIZE - 1 - i][i]
    }
    if (string == firstPlayerWon) result = 1
    if (string == secondPlayerWon) result = 2
    return result
}

fun Array<CharArray>.getPossibleMoves(): List<Move> {
    val list = mutableListOf<Move>()
    for (row in 0 until SIZE) {
        for (column in 0 until SIZE) {
            if (this[row][column] == ' ') {
                list += Move(row, column)
            }
        }
    }
    return list
}

fun Array<CharArray>.isFinal() = this.getPossibleMoves().isEmpty()
