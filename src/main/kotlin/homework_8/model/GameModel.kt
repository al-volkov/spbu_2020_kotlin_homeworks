package homework_8.model

import homework_8.Bot
import homework_8.model.FieldsChecker.getWinner
import homework_8.GameController
import homework_8.RandomBot

class GameModel(private val controller: GameController) {
    companion object {
        const val MIN_NUMBER_OF_MOVES_TO_WIN = 5
        const val MAX_NUMBER_OF_MOVES = 9
        const val SIZE = 3
    }
    private var isFinished = false
    val board = Array(GameController.SIZE) { CharArray(GameController.SIZE) { ' ' } }
    var playerSymbol = 'X'
    var botSymbol = '0'
    var numberOfMoves = 0
    var isBotEnabled = false
    var bot: Bot = RandomBot(controller)

    fun activateBot() {
        if (isBotEnabled && !isFinished) {
            if (numberOfMoves % 2 == 0 && botSymbol == 'X') {
                bot.makeMove()
            }
            if (numberOfMoves % 2 == 1 && botSymbol == '0') {
                bot.makeMove()
            }
        }
    }

    fun makeMove(row: Int, column: Int) {
        board[row][column] = if (numberOfMoves % 2 == 0) {
            'X'
        } else {
            '0'
        }
        ++numberOfMoves
        if (numberOfMoves >= MIN_NUMBER_OF_MOVES_TO_WIN) {
            val winner = board.getWinner()
            if (winner != 0 || numberOfMoves == MAX_NUMBER_OF_MOVES) {
                controller.finishGame(winner)
                isFinished = true
            }
        }
    }

    private fun updateField() {
        for (row in 0 until SIZE) {
            for (column in 0 until SIZE) {
                board[row][column] = ' '
            }
        }
    }

    fun update() {
        updateField()
        numberOfMoves = 0
        isFinished = false
    }
}
