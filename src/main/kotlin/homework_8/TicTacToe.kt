package homework_8

import javafx.scene.layout.VBox
import tornadofx.Controller
import tornadofx.App
import tornadofx.View
import tornadofx.text
import tornadofx.gridpane
import tornadofx.row
import tornadofx.button
import tornadofx.action

const val SIZE = 3

class TicTacToe : App(GameView::class)

class GameController : Controller() {
    private val fields = Array(SIZE) { CharArray(SIZE) { ' ' } }
    var lastMove = 0
    fun changeField(row: Int, column: Int) {
        if (lastMove == 0) {
            fields[row][column] = 'X'
        } else {
            fields[row][column] = '0'
        }
        ++lastMove
        lastMove %= 2
        val button = find<GameView>().root.lookup("#$row$column")
        button.text(fields[row][column].toString())
        button.isDisable = true
    }
}

class GameView : View("Tic Tac Toe") {
    private val controller: GameController by inject()
    override val root = VBox()

    init {
        root.apply {
            gridpane {
                for (i in 0 until 3) {
                    row {
                        for (j in 0 until 3) {
                            button() {
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
