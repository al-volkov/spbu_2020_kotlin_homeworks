package homework_8

import homework_8.model.GameModel
import homework_8.model.Move
import homework_8.model.MultiplayerModel
import homework_8.model.OnePlayerModel
import homework_8.model.Player
import homework_8.model.TwoPlayersModel
import homework_8.views.FinalView
import homework_8.views.GameView
import homework_8.views.ResultFragment
import homework_8.views.StartView
import javafx.application.Platform
import tornadofx.Controller
import tornadofx.text

class GameController : Controller() {
    companion object {
        const val SIZE = 3
    }

    var model: GameModel = TwoPlayersModel(this)

    var isBotEnabled = false
    var isBotDifficult = false
    var player: Player = Player.FIRST
    var isMultiplayerMode = false
    fun startGame() {
        updateButtons()
        updateModel()
        find<StartView>().replaceWith<GameView>()
    }

    fun makeMove(move: Move) {
        model.makeMove(move)
    }

    private fun updateModel() {
        model = when {
            isBotEnabled -> OnePlayerModel(player, isBotDifficult, this)
            isMultiplayerMode -> MultiplayerModel(this)
            else -> TwoPlayersModel(this)
        }
    }

    fun changeButton(row: Int, column: Int, symbol: Char) {
        val button = find<GameView>().root.lookup("#$row$column")
        button.isDisable = true
        button.text(symbol.toString())
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

    fun finishGame(winner: Player?) {
        Platform.runLater {
            find<GameView>().replaceWith<FinalView>()
        }
        val resultFragment = ResultFragment(winner?.winMessage ?: "Draw")
        Platform.runLater {
            resultFragment.openModal()
        }
    }

    fun disableAllButtons() {
        val gameView = find<GameView>()
        for (row in 0 until SIZE) {
            for (column in 0 until SIZE) {
                val button = gameView.root.lookup("#$row$column")
                button.isDisable = true
            }
        }
    }

    fun enableButton(row: Int, column: Int) {
        find<GameView>().root.lookup("#$row$column").isDisable = false
    }

    fun restartGame() {
        find<FinalView>().replaceWith<StartView>()
    }
}
