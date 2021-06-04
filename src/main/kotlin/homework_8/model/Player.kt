package homework_8.model

enum class Player(val symbol: Char, val winMessage: String) {
    FIRST('X', "X won"), SECOND('0', "0 won");

    fun getOpponent() = if (this == FIRST) SECOND else FIRST
}
