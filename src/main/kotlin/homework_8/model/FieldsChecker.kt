package homework_8.model

object FieldsChecker {
    private const val SIZE = 3
    private const val FIRST_PLAYER_WON = "XXX"
    private const val SECOND_PLAYER_WON = "000"
    fun Array<CharArray>.getWinner(): Int {
        return (this.checkRows() + this.checkColumns() + this.checkDiagonal())
    }

    private fun Array<CharArray>.checkRows(): Int {
        var result = 0
        for (row in this) {
            if (row.joinToString("") == FIRST_PLAYER_WON) result = 1
            if (row.joinToString("") == SECOND_PLAYER_WON) result = 2
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
            if (string == FIRST_PLAYER_WON) result = 1
            if (string == SECOND_PLAYER_WON) result = 2
        }
        return result
    }

    private fun Array<CharArray>.checkDiagonal(): Int {
        var result = 0
        var string = ""
        for (i in 0 until SIZE) {
            string += this[i][i]
        }
        if (string == FIRST_PLAYER_WON) result = 1
        if (string == SECOND_PLAYER_WON) result = 2
        string = ""
        for (i in 0 until SIZE) {
            string += this[SIZE - 1 - i][i]
        }
        if (string == FIRST_PLAYER_WON) result = 1
        if (string == SECOND_PLAYER_WON) result = 2
        return result
    }

    fun Array<CharArray>.getPossibleMoves(): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()
        for (row in 0 until SIZE) {
            for (column in 0 until SIZE) {
                if (this[row][column] == ' ') {
                    list += Pair(row, column)
                }
            }
        }
        return list
    }

    fun Array<CharArray>.isFinal() = this.getPossibleMoves().isEmpty()
}
