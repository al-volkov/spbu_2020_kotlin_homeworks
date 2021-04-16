package homework_8

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import tornadofx.Stylesheet.Companion.line
import tornadofx.Stylesheet.Companion.root
import tornadofx.Stylesheet.Companion.rowSelection


class TicTacToe : App(GameView::class) {
}

class GameController : Controller() {
    private val fields = Array(3) { CharArray(3) { ' ' } }
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
                                val row = i
                                val column = j
                                id = "$i$j"
                                action { controller.changeField(row, column) }
                            }
                        }
                    }
                }
            }
        }
    }
}
