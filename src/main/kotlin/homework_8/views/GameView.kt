package homework_8.views

import homework_8.GameController
import homework_8.GameStylesheet
import javafx.geometry.Pos
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.gridpane
import tornadofx.row
import tornadofx.text
import tornadofx.vbox

class GameView : View("Tic Tac Toe") {
    companion object {
        const val SIZE = 3
    }

    private val controller: GameController by inject()
    override val root = vbox {
        this.alignment = Pos.CENTER
        addClass(GameStylesheet.gameWindow)
        this.apply {
            gridpane {
                this.alignment = Pos.CENTER
                for (row in 0 until SIZE) {
                    row {
                        for (column in 0 until SIZE) {
                            button {
                                text(" ")
                                addClass(GameStylesheet.gameButton)
                                id = "$row$column"
                                action {
                                    controller.makeMove(row, column)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
