package homework_8.views

import homework_8.GameController
import homework_8.GameStylesheet
import homework_8.model.Player
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
                    controller.isBotEnabled = false
                    controller.isMultiplayerMode = false
                    controller.startGame()
                }
            }
            button("Play with bot") {
                addClass(GameStylesheet.menuButton)
                action {
                    controller.isBotEnabled = true
                    controller.isMultiplayerMode = false
                    find<ChooseSideFragment>().openModal()
                }
            }
            button("Play with other player") {
                addClass(GameStylesheet.menuButton)
                action {
                    controller.isBotEnabled = false
                    controller.isMultiplayerMode = true
                    controller.startGame()
                }
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
        button(Player.FIRST.symbol.toString()) {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.player = Player.FIRST
                find<ChooseDifficultyFragment>().openModal()
                fragment.close()
            }
        }
        button(Player.SECOND.symbol.toString()) {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.player = Player.SECOND
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
                controller.isBotDifficult = false
                controller.startGame()
                fragment.close()
            }
        }
        button("Difficult bot") {
            addClass(GameStylesheet.menuFragmentButton)
            action {
                controller.isBotDifficult = true
                controller.startGame()
                fragment.close()
            }
        }
    }
}
