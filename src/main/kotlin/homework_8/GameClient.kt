package homework_8

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocket
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.close
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameClient(private val controller: GameController) {
    companion object {
        const val SYMBOL_START = "symbol:"
        const val MOVE_START = "move:"
        const val ZERO_CODE = '0'.toInt()
    }

    fun start() {
        val client = HttpClient {
            install(WebSockets)
        }
        runBlocking {
            client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/") {
                controller.socket = this
                val incomingMessageRoutine = launch { incomingInformation() }
                val checkerRoutine = launch { resultChecker() }
                checkerRoutine.join()
                incomingMessageRoutine.cancelAndJoin()
            }
        }
        client.close()
    }

    private suspend fun DefaultClientWebSocketSession.incomingInformation() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val receivedText = message.readText()
                println(receivedText)
                handleInformation(receivedText)
            }
        } finally {
            close()
        }
    }

    private fun resultChecker() {
        while (true) {
            if (controller.model.isFinished) {
                return
            }
        }
    }

    private fun handleInformation(text: String) {
        when {
            text.startsWith(SYMBOL_START) -> handleSymbol(text)
            text.startsWith(MOVE_START) -> handleMove(text)
        }
    }

    private fun handleSymbol(text: String) {
        val playersSymbol = text.replace(SYMBOL_START, "").first()
        controller.model.playerSymbol = playersSymbol
        if (playersSymbol == '0') {
            controller.model.isWaitingForMove = true
        } else {
            controller.enableRequiredButtons()
        }
    }

    private fun handleMove(text: String) {
        val moveText = text.replace(MOVE_START, "")
        controller.makeMove(moveText[0].toInt() - ZERO_CODE, moveText[1].toInt() - ZERO_CODE)
        controller.enableRequiredButtons()
        controller.model.isWaitingForMove = false
    }
}
