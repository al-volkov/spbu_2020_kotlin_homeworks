package homework_8

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass
import tornadofx.mm
import tornadofx.multi

class GameStylesheet : Stylesheet() {
    companion object {
        val menuWindow by cssclass()
        private val menuWindowHeight = 100.mm
        private val menuWindowWidth = 150.mm
        private val windowBackgroundColor = c("#ccff99")
        val menuButton by cssclass()
        private val menuButtonHeight = 20.mm
        private val menuButtonWidth = 55.mm
        private val menuButtonColor = c("#ff6666")
        private val menuButtonFontSize = 5.mm
        val gameWindow by cssclass()
        val gameButton by cssclass()
        private val gameButtonHeight = 30.mm
        private val gameButtonWidth = 30.mm
        private val gameButtonColor = c("#33cc33")
        private val gameButtonFontSize = 15.mm
        private val gameButtonFontWeight = FontWeight.BOLD
        private val gameButtonTextColor = c("#666633")
        val menuFragment by cssclass()
        private val menuFragmentHeight = 50.mm
        private val menuFragmentWidth = 90.mm
        private val menuFragmentColor = c("#33ccff")
        val menuFragmentButton by cssclass()
        private val menuFragmentButtonHeight = 15.mm
        private val menuFragmentButtonWidth = 50.mm
        private val menuFragmentButtonColor = c("#ff9900")
        private val menuFragmentButtonFontSize = 5.mm
        val resultFragment by cssclass()
        private val resultFragmentHeight = 50.mm
        private val resultFragmentWidth = 90.mm
        private val resultFragmentColor = c("#33ccff")
        private val resultFragmentFontSize = 8.mm
    }

    init {
        menuWindow {
            prefHeight = menuWindowHeight
            prefWidth = menuWindowWidth
            backgroundColor = multi(windowBackgroundColor)
        }
        menuButton {
            prefHeight = menuButtonHeight
            prefWidth = menuButtonWidth
            baseColor = menuButtonColor
            fontSize = menuButtonFontSize
        }
        gameWindow {
            backgroundColor = multi(windowBackgroundColor)
        }
        gameButton {
            prefHeight = gameButtonHeight
            prefWidth = gameButtonWidth
            baseColor = gameButtonColor
            fontSize = gameButtonFontSize
            fontWeight = gameButtonFontWeight
            textFill = gameButtonTextColor
        }
        menuFragment {
            prefHeight = menuFragmentHeight
            prefWidth = menuFragmentWidth
            baseColor = menuFragmentColor
        }
        menuFragmentButton {
            prefHeight = menuFragmentButtonHeight
            prefWidth = menuFragmentButtonWidth
            baseColor = menuFragmentButtonColor
            fontSize = menuFragmentButtonFontSize
        }
        resultFragment {
            prefHeight = resultFragmentHeight
            prefWidth = resultFragmentWidth
            baseColor = resultFragmentColor
            fontSize = resultFragmentFontSize
        }
    }
}
