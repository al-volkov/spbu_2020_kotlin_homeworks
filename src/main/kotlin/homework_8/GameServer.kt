package homework_8

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.send
import io.ktor.routing.routing
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import java.util.Collections
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(WebSockets)
    routing {
        val connections = Collections.synchronizedSet<Player?>(LinkedHashSet())
        webSocket("/") {
            val thisConnection = Player(this)
            connections += thisConnection
            try {
                if (connections.size == 2) {
                    connections.elementAt(0).session.send("symbol:X")
                    connections.elementAt(1).session.send("symbol:0")
                }
                if (connections.size > 2) {
                    throw IllegalArgumentException("too many players")
                }
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    connections.elementAt((thisConnection.id + 1) % 2).session.send("move:$receivedText")
                }
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
            }
        }
    }
}

class Player(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val id = lastId.getAndIncrement()
    val name = "player$id"
}
