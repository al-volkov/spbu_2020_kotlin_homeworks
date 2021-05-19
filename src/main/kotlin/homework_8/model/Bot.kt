package homework_8

import homework_8.model.FieldsChecker.getPossibleMoves
import homework_8.model.FieldsChecker.getWinner
import homework_8.model.FieldsChecker.isFinal
import kotlin.math.max
import kotlin.math.min

abstract class Bot(protected val controller: GameController) {
    abstract fun makeMove()
}

class RandomBot(controller: GameController) : Bot(controller) {
    override fun makeMove() {
        val possibleMoves = controller.model.board.getPossibleMoves()
        val move = possibleMoves.random()
        controller.makeMove(move.first, move.second)
    }
}

class RationalBot(controller: GameController) : Bot(controller) {
    companion object {
        const val MIN_SCORE = -1000
        const val MAX_SCORE = 1000
        const val PLAYER_VALUE = 10
        const val OPPONENT_VALUE = -10
    }

    private var playersSymbol: Char = ' '
    private var opponentSymbol: Char = ' '
    override fun makeMove() {
        val model = controller.model
        playersSymbol = model.opponentSymbol
        opponentSymbol = model.playerSymbol
        val move = if (controller.model.numberOfMoves == 0) {
            Pair(1, 1)
        } else {
            model.board.findBestMove()
        }
        controller.makeMove(move.first, move.second)
    }

    private fun Array<CharArray>.evaluateScore(): Int {
        val winnerSymbol = when (this.getWinner()) {
            1 -> 'X'
            2 -> '0'
            else -> ' '
        }
        return when (winnerSymbol) {
            playersSymbol -> PLAYER_VALUE
            opponentSymbol -> OPPONENT_VALUE
            else -> 0
        }
    }

    private fun Array<CharArray>.minimax(isMaximizing: Boolean): Int {
        val score = this.evaluateScore()
        if (score == PLAYER_VALUE || score == OPPONENT_VALUE || this.isFinal()) {
            return score
        }
        var best: Int
        if (isMaximizing) {
            best = MIN_SCORE
            for (move in this.getPossibleMoves()) {
                this[move.first][move.second] = playersSymbol
                best = max(best, this.minimax(!isMaximizing))
                this[move.first][move.second] = ' '
            }
        } else {
            best = MAX_SCORE
            for (move in this.getPossibleMoves()) {
                this[move.first][move.second] = opponentSymbol
                best = min(best, this.minimax(!isMaximizing))
                this[move.first][move.second] = ' '
            }
        }
        return best
    }

    private fun Array<CharArray>.findBestMove(): Pair<Int, Int> {
        var bestScore = MIN_SCORE
        var bestMove = Pair(-1, -1)
        for (move in this.getPossibleMoves()) {
            this[move.first][move.second] = playersSymbol
            val newScore = this.minimax(false)
            this[move.first][move.second] = ' '
            if (newScore > bestScore) {
                bestScore = newScore
                bestMove = move
            }
        }
        return bestMove
    }
}
