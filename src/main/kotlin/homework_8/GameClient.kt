package homework_8

import homework_8.model.Move.Companion.getMove
import homework_8.model.MultiplayerModel
import homework_8.model.Player
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

class GameClient(private val model: MultiplayerModel) {
    fun start() {
        val client = HttpClient {
            install(WebSockets)
        }
        runBlocking {
            client.webSocket(method = HttpMethod.Get, host = HOST, port = PORT, path = PATH) {
                model.socket = this
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
                handleInformation(receivedText)
            }
        } finally {
            close()
        }
    }

    private fun resultChecker() {
        while (true) {
            if (model.isFinished) {
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
        when (text.replace(SYMBOL_START, "").first()) {
            Player.FIRST.symbol -> {
                model.playerSymbol = Player.FIRST.symbol
                model.opponentSymbol = Player.SECOND.symbol
                model.enableRequiredButtons()
            }
            Player.SECOND.symbol -> {
                model.playerSymbol = Player.SECOND.symbol
                model.opponentSymbol = Player.FIRST.symbol
                model.isWaitingForMove = true
            }
        }
    }

    private fun handleMove(text: String) {
        model.handleMultiplayerMove(text.getMove())
    }

    companion object {
        const val SYMBOL_START = "symbol:"
        const val MOVE_START = "move:"
        const val HOST = "127.0.0.1"
        const val PORT = 8080
        const val PATH = "/"
    }
}
