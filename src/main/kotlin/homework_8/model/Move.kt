package homework_8.model

import homework_8.GameClient

data class Move(val row: Int, val column: Int) {
    companion object {
        fun String.getMove(): Move {
            val text = this.replace(GameClient.MOVE_START, "")
            return Move(text[0] - '0', text[1] - '0')
        }
    }
}
