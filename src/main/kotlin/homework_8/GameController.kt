package homework_8

import homework_8.model.GameModel
import homework_8.views.FinalView
import homework_8.views.GameView
import homework_8.views.ResultFragment
import homework_8.views.StartView
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import tornadofx.Controller
import tornadofx.text
import javafx.application.Platform
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameController : Controller() {
    companion object {
        const val SIZE = 3
    }

    val client = GameClient(this)
    val model = GameModel(this)
    lateinit var socket: DefaultClientWebSocketSession
    fun startGame() {
        find<StartView>().replaceWith<GameView>()
        model.activateBot()
    }

    fun makeMove(row: Int, column: Int) {
        if (model.isMoveToSend) {
            runBlocking {
                multiplayerMove(row, column)
            }
        }
        model.makeMove(row, column)
        if (model.isMultiplayerMode) {
            Platform.runLater {
                changeButton(row, column)
            }
        } else {
            changeButton(row, column)
        }
        model.activateBot()
    }

    private fun changeButton(row: Int, column: Int) {
        val button = find<GameView>().root.lookup("#$row$column")
        button.isDisable = true
        if (model.numberOfMoves % 2 == 1) {
            button.text("X")
        } else {
            button.text("0")
        }
    }

    private suspend fun multiplayerMove(row: Int, column: Int) {
        socket.send(Frame.Text("$row$column"))
        model.isMoveToSend = false
        disableAllButtons()
        model.isWaitingForMove = true
    }

    fun restartGame() {
        model.update()
        updateButtons()
        find<FinalView>().replaceWith<StartView>()
    }

    private fun updateButtons() {
        val gameView = find<GameView>()
        for (row in 0 until SIZE) {
            for (column in 0 until SIZE) {
                val button = gameView.root.lookup("#$row$column")
                button.isDisable = false
                button.text(" ")
            }
        }
    }

    fun finishGame(winner: Int) {
        runBlocking {
            socket.close()
        }
        find<GameView>().replaceWith<FinalView>()
        val resultFragment = ResultFragment(
            when (winner) {
                1 -> "First player won"
                2 -> "Second player won"
                else -> "Draw"
            }
        )
        resultFragment.openModal()
    }

    fun startMultiplayer() {
        find<StartView>().replaceWith<GameView>()
        disableAllButtons()
        GlobalScope.launch { client.start() }
    }

    private fun disableAllButtons() {
        val gameView = find<GameView>()
        for (row in 0 until GameModel.SIZE) {
            for (column in 0 until GameModel.SIZE) {
                val button = gameView.root.lookup("#$row$column")
                button.isDisable = true
            }
        }
    }

    fun enableRequiredButtons() {
        val gameView = find<GameView>()
        for (row in 0 until GameModel.SIZE) {
            for (column in 0 until GameModel.SIZE) {
                if (model.board[row][column] == ' ') {
                    val button = gameView.root.lookup("#$row$column")
                    button.isDisable = false
                }
            }
        }
    }
}
