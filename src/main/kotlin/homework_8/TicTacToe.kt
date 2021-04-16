package homework_8

import javafx.scene.layout.VBox
import tornadofx.App
import tornadofx.Controller
import tornadofx.text
import tornadofx.View
import tornadofx.gridpane
import tornadofx.row
import tornadofx.button
import tornadofx.action
import tornadofx.vbox
import java.lang.IllegalArgumentException

const val MIN_NUMBER_OF_MOVES_TO_WIN = 5
const val MAX_NUMBER_OF_MOVES = 9
const val SIZE = 3

class TicTacToe : App(StartView::class)

class GameController : Controller() {
    private val fields = Array(SIZE) { CharArray(SIZE) { ' ' } }
    private var lastMove = 0
    private var numberOfMoves = 0
    fun changeField(row: Int, column: Int) {
        if (lastMove == 0) {
            fields[row][column] = 'X'
        } else {
            fields[row][column] = '0'
        }
        ++lastMove
        lastMove %= 2
        ++numberOfMoves
        val button = find<GameView>().root.lookup("#$row$column")
        button.text(fields[row][column].toString())
        button.isDisable = true
        checkWin()
    }

    private fun checkWinner(string: String) {
        when (string) {
            "XXX" -> finishGame(1)
            "000" -> finishGame(0)
        }
    }

    private fun checkWin() {
        if (numberOfMoves >= MIN_NUMBER_OF_MOVES_TO_WIN) {
            if (numberOfMoves == MAX_NUMBER_OF_MOVES) {
                finishGame(0)
            }
            fields.forEach {
                if (it.joinToString("") == "XXX") finishGame(1)
                if (it.joinToString("") == "000") finishGame(2)
            }
            var string = ""
            for (numberOfColumn in 0..2) {
                for (row in 0..2) {
                    string += fields[row][numberOfColumn]
                }
                checkWinner(string)
                string = ""
            }
            for (i in 0..2) {
                string += fields[i][i]
            }
            checkWinner(string)
            string = ""
            for (i in 0..2) {
                string += fields[2 - i][i]
            }
            checkWinner(string)
        }
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
    }

    private fun finishGame(result: Int) {
        find<GameView>().replaceWith<FinalView>()
        find<FinalView>().root.text(
            when (result) {
                0 -> "Draw"
                1 -> "First player won"
                2 -> "Second player won"
                else -> throw IllegalArgumentException()
            }
        )
    }
}

class GameView : View("Tic Tac Toe") {
    private val controller = GameController()
    override val root = VBox()

    init {
        root.apply {
            gridpane {
                for (i in 0 until SIZE) {
                    row {
                        for (j in 0 until SIZE) {
                            button {
                                id = "$i$j"
                                action { controller.changeField(i, j) }
                            }
                        }
                    }
                }
            }
        }
    }
}

class FinalView : View("Tic Tac Toe") {
    override val root = vbox {
        this.apply { text("Game over") }
    }
}

class StartView : View("Tic Tac Toe") {
    private val controller = GameController()
    override val root = vbox {
        button("Start") {
            action { controller.startGame() }
        }
    }
}
