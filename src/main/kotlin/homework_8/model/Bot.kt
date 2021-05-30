package homework_8

import homework_8.model.getPossibleMoves
import homework_8.model.getWinner
import homework_8.model.isFinal
import homework_8.model.Move
import homework_8.model.OnePlayerModel
import kotlin.math.max
import kotlin.math.min

abstract class Bot(val model: OnePlayerModel) {
    abstract fun getMove(): Move
}

class RandomBot(model: OnePlayerModel) : Bot(model) {
    override fun getMove() = model.board.getPossibleMoves().random()
}

class RationalBot(model: OnePlayerModel) : Bot(model) {
    companion object {
        const val MIN_SCORE = -1000
        const val MAX_SCORE = 1000
        const val PLAYER_VALUE = 10
        const val OPPONENT_VALUE = -10
    }

    private var playersSymbol: Char = ' '
    private var opponentSymbol: Char = ' '
    override fun getMove(): Move {
        playersSymbol = model.botSymbol
        opponentSymbol = model.playersSymbol
        return if (model.numberOfMoves == 0) {
            Move(1, 1)
        } else {
            model.board.findBestMove()
        }
    }

    private fun Array<CharArray>.evaluateScore(): Int {
        return when (this.getWinner()?.symbol ?: ' ') {
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
                this[move.row][move.column] = playersSymbol
                best = max(best, this.minimax(!isMaximizing))
                this[move.row][move.column] = ' '
            }
        } else {
            best = MAX_SCORE
            for (move in this.getPossibleMoves()) {
                this[move.row][move.column] = opponentSymbol
                best = min(best, this.minimax(!isMaximizing))
                this[move.row][move.column] = ' '
            }
        }
        return best
    }

    private fun Array<CharArray>.findBestMove(): Move {
        var bestScore = MIN_SCORE
        var bestMove = Move(-1, -1)
        for (move in this.getPossibleMoves()) {
            this[move.row][move.column] = playersSymbol
            val newScore = this.minimax(false)
            this[move.row][move.column] = ' '
            if (newScore > bestScore) {
                bestScore = newScore
                bestMove = move
            }
        }
        return bestMove
    }
}
