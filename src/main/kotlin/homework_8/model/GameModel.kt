package homework_8.model

import homework_8.Bot
import homework_8.GameClient
import homework_8.model.getWinner
import homework_8.GameController
import homework_8.RandomBot
import homework_8.RationalBot
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.Frame
import javafx.application.Platform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class GameModel(protected val controller: GameController) {
    protected companion object {
        const val MIN_NUMBER_OF_MOVES_TO_WIN = 5
        const val MAX_NUMBER_OF_MOVES = 9
        const val SIZE = 3
    }

    var isFinished = false
    var numberOfMoves = 0
    val board = Array(GameController.SIZE) { CharArray(GameController.SIZE) { ' ' } }
    abstract fun makeMove(move: Move)
}

class TwoPlayersModel(controller: GameController) : GameModel(controller) {
    private val currentSymbol
        get() = if (numberOfMoves % 2 == 0) {
            Player.FIRST.symbol
        } else {
            Player.SECOND.symbol
        }

    override fun makeMove(move: Move) {
        controller.changeButton(move.row, move.column, currentSymbol)
        board[move.row][move.column] = currentSymbol
        ++numberOfMoves
        checkWin()
    }

    private fun checkWin() {
        if (numberOfMoves >= MIN_NUMBER_OF_MOVES_TO_WIN) {
            val winner = board.getWinner()
            if (winner != null || numberOfMoves == MAX_NUMBER_OF_MOVES) {
                isFinished = true
                controller.finishGame(winner)
            }
        }
    }
}

class OnePlayerModel(player: Player, isBotDifficult: Boolean, controller: GameController) :
    GameModel(controller) {
    private val bot: Bot = if (isBotDifficult) RationalBot(this) else RandomBot(this)
    val botSymbol = player.getOpponent().symbol
    val playersSymbol = player.symbol

    init {
        if (player == Player.SECOND) {
            handleBot()
        }
    }

    private fun checkWin() {
        if (numberOfMoves >= MIN_NUMBER_OF_MOVES_TO_WIN) {
            val winner = board.getWinner()
            if (winner != null || numberOfMoves == MAX_NUMBER_OF_MOVES) {
                isFinished = true
                controller.finishGame(winner)
            }
        }
    }

    override fun makeMove(move: Move) {
        controller.changeButton(move.row, move.column, playersSymbol)
        board[move.row][move.column] = playersSymbol
        ++numberOfMoves
        checkWin()
        if (!isFinished) {
            handleBot()
        }
    }

    private fun handleBot() {
        val botMove = bot.getMove()
        board[botMove.row][botMove.column] = botSymbol
        ++numberOfMoves
        if (numberOfMoves == 1) {
            controller.changeButton(botMove.row, botMove.column, botSymbol)
        }
        controller.changeButton(botMove.row, botMove.column, botSymbol)
        checkWin()
    }
}

class MultiplayerModel(controller: GameController) : GameModel(controller) {
    lateinit var socket: DefaultClientWebSocketSession
    var playerSymbol = Player.FIRST.symbol
    var opponentSymbol = Player.SECOND.symbol
    var isWaitingForMove = false

    init {
        controller.disableAllButtons()
        val model = this
        GlobalScope.launch {
            GameClient(model).start()
        }
    }

    override fun makeMove(move: Move) {
        controller.changeButton(move.row, move.column, playerSymbol)
        controller.disableAllButtons()
        board[move.row][move.column] = playerSymbol
        ++numberOfMoves
        isWaitingForMove = true
        runBlocking {
            socket.send(Frame.Text("${move.row}${move.column}"))
        }
        checkWin()
    }

    private fun checkWin() {
        if (numberOfMoves >= MIN_NUMBER_OF_MOVES_TO_WIN) {
            val winner = board.getWinner()
            println(winner)
            if (winner != null || numberOfMoves == MAX_NUMBER_OF_MOVES) {
                isFinished = true
                runBlocking { socket.close() }
                controller.finishGame(winner)
            }
        }
    }

    fun handleMultiplayerMove(move: Move) {
        board[move.row][move.column] = opponentSymbol
        Platform.runLater {
            controller.changeButton(move.row, move.column, opponentSymbol)
        }
        ++numberOfMoves
        isWaitingForMove = false
        enableRequiredButtons()
        checkWin()
    }

    fun enableRequiredButtons() {
        for (row in 0 until SIZE) {
            for (column in 0 until SIZE) {
                if (board[row][column] == ' ') {
                    controller.enableButton(row, column)
                }
            }
        }
    }
}
