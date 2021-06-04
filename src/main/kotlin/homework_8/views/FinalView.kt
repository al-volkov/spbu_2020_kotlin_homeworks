package homework_8.views

import homework_8.GameController
import homework_8.GameStylesheet
import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.text
import tornadofx.vbox

class FinalView : View("Tic Tac Toe") {
    private val controller: GameController by inject()
    override val root = vbox {
        addClass(GameStylesheet.menuWindow)
        this.alignment = Pos.CENTER
        this.apply {
            button("Restart") {
                addClass(GameStylesheet.menuButton)
                action {
                    controller.restartGame()
                }
            }
        }
    }
}

class ResultFragment(text: String) : Fragment("Result") {
    override val root = vbox {
        addClass(GameStylesheet.resultFragment)
        this.alignment = Pos.CENTER
        this.text(text)
    }
}
