package homework_8.views

import homework_8.GameController
import homework_8.GameStylesheet
import homework_8.RandomBot
import homework_8.RationalBot
import javafx.geometry.Pos
import tornadofx.Fragment
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.vbox

class StartView : View("Tic Tac Toe") {
    private val controller: GameController by inject()
    override val root = vbox {
        addClass(GameStylesheet.menuWindow)
        this.alignment = Pos.CENTER
        this.apply {
            button("Play with yourself") {
                addClass(GameStylesheet.menuButton)
                action {
                    controller.model.isBotEnabled = false
                    controller.startGame()
                }
            }
            button("Play with bot") {
                addClass(GameStylesheet.menuButton)
                action {
                    controller.model.isBotEnabled = true
                    find<ChooseSideFragment>().openModal()
                }
            }
            button("Play with other player") {
                addClass(GameStylesheet.menuButton)
            }
        }
    }
}

class ChooseSideFragment : Fragment() {
    private val fragment = this
    private val controller: GameController by inject()
    override val root = vbox {
        addClass(GameStylesheet.menuFragment)
        this.alignment = Pos.CENTER
        button("X") {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.model.botSymbol = '0'
                controller.model.playerSymbol = 'X'
                find<ChooseDifficultyFragment>().openModal()
                fragment.close()
            }
        }
        button("0") {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.model.botSymbol = 'X'
                controller.model.playerSymbol = '0'
                find<ChooseDifficultyFragment>().openModal()
                fragment.close()
            }
        }
    }
}

class ChooseDifficultyFragment : Fragment() {
    private val controller: GameController by inject()
    private val fragment = this
    override val root = vbox {
        this.alignment = Pos.CENTER
        addClass(GameStylesheet.menuFragment)
        button("Easy bot") {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.model.bot = RandomBot(controller)
                controller.startGame()
                fragment.close()
            }
        }
        button("Difficult bot") {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.model.bot = RationalBot(controller)
                controller.startGame()
                fragment.close()
            }
        }
    }
}
