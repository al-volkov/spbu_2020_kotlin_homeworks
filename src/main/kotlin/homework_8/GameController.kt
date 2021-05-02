package homework_8

import homework_8.model.GameModel
import homework_8.views.FinalView
import homework_8.views.GameView
import homework_8.views.ResultFragment
import homework_8.views.StartView
import tornadofx.Controller
import tornadofx.text

class GameController : Controller() {
    companion object {
        const val SIZE = 3
    }

    val model = GameModel(this)
    fun startGame() {
        find<StartView>().replaceWith<GameView>()
        model.activateBot()
    }

    fun makeMove(row: Int, column: Int) {
        model.makeMove(row, column)
        val button = find<GameView>().root.lookup("#$row$column")
        button.isDisable = true
        if (model.numberOfMoves % 2 == 1) {
            button.text("X")
        } else {
            button.text("0")
        }
        model.activateBot()
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
}
