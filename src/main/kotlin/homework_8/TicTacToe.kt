package homework_8

import homework_8.views.StartView
import javafx.stage.Stage
import tornadofx.App

class TicTacToe : App(StartView::class, GameStylesheet::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}
