package com.piconemarc.calculator.reducer.screenReducer

import android.util.Log
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.Reducer
import com.piconemarc.calculator.viewModel.isOperationTrue
import kotlinx.coroutines.coroutineScope
import kotlin.random.Random

//todo review to reduce code
val gameReducer: Reducer<GameState> = { old, action ->
    action as GameAction
    when (action) {
        is GameAction.StartGame -> {
            val firstNumber = action.gameParameters.tableList.random()
            val operand = action.gameParameters.operandList.random()
            val secondNumber = Random.nextInt(1, 10)
            val goodAnswer = when (operand) {
                "+" -> (firstNumber + secondNumber).toString()
                "-" -> (firstNumber - secondNumber).toString()
                "*" -> (firstNumber * secondNumber).toString()
                else -> ""
            }

            val noviceAnswerValue = mutableListOf(
                goodAnswer.toInt(),
                calculateRandomResult(goodAnswer),
                calculateRandomResult(goodAnswer),
                calculateRandomResult(goodAnswer)
            )
            noviceAnswerValue.shuffle()

            old.copy(
                firstNumber = firstNumber,
                operand = operand,
                secondNumber = secondNumber,
                questionCounter = 0,
                result = "",
                score = 0,
                bonus = 1,
                goodAnswerChain = 0,
                gameParameters = action.gameParameters,
                noviceAnswerValues = noviceAnswerValue
            )
        }

        is GameAction.UpdateOperation -> {
            val firstNumber = action.gameState.gameParameters.tableList.random()
            val operand = action.gameState.gameParameters.operandList.random()
            val secondNumber = Random.nextInt(1, 10)
            val goodAnswer = when (operand) {
                "+" -> (firstNumber + secondNumber).toString()
                "-" -> (firstNumber - secondNumber).toString()
                "*" -> (firstNumber * secondNumber).toString()
                else -> ""
            }

            val noviceAnswerValue = mutableListOf(
                goodAnswer.toInt(),
                calculateRandomResult(goodAnswer),
                calculateRandomResult(goodAnswer),
                calculateRandomResult(goodAnswer)
            )
            noviceAnswerValue.shuffle()

            Log.i("TAG", "reduce: $operand")
            old.copy(
                firstNumber = firstNumber,
                operand = operand,
                secondNumber = secondNumber,
                questionCounter = action.gameState.questionCounter + 1,
                result = "",
                noviceAnswerValues = noviceAnswerValue
            )
        }

        is GameAction.UpdateGoodAnswerChainCount -> {
            val goodAnswerChainCount = if (action.gameState.firstNumber.isOperationTrue(
                    action.gameState.operand,
                    action.gameState.secondNumber,
                    action.gameState.result
                )
            ) action.gameState.goodAnswerChain + 1 else 0
            old.copy(
                goodAnswerChain = goodAnswerChainCount,
                bonus = when (goodAnswerChainCount) {
                    3, 4 -> 300
                    5, 6 -> 600
                    7, 8, 9 -> 1000
                    else -> {
                        if (goodAnswerChainCount > 10) 2000
                        else 1
                    }
                }
            )
        }
        is GameAction.UpdateRemainingTime -> old.copy(
            remainingTime = action.remainingTime / 1000
        )
        is GameAction.UpdateResult -> old.copy(
            result = action.result
        )
        is GameAction.UpdateScore -> {
            old.copy(
                score = if (action.gameState.firstNumber.isOperationTrue(
                        action.gameState.operand,
                        action.gameState.secondNumber,
                        action.gameState.result
                    )
                ) {
                    action.gameState.score + (calculateScore(action.gameState) * action.gameState.bonus)
                } else action.gameState.score,
            )
        }
        is GameAction.TimesUp -> old
        is GameAction.SubmitResult -> old
        is GameAction.StartAnswerChrono -> old
        is GameAction.UpdateAnswerChrono -> old.copy(
            answerTime = action.time
        )
    }
}

private fun calculateRandomResult(goodAnswer: String): Int {
    val randomModifierBound = if (goodAnswer.toInt() > 1) goodAnswer.toInt()
    else Random.nextInt(3, 10)

    val randomValueModifier = Random.nextInt(1, randomModifierBound)

    return if (goodAnswer.toInt() - randomValueModifier < 2) {
        goodAnswer.toInt() + randomValueModifier
    } else {
        goodAnswer.toInt() - randomValueModifier
    }
}

fun calculateScore(gameState: GameState): Int = when (gameState.gameParameters.gameLevel) {
    GameLevel.NOVICE -> {
        GameLevel.NOVICE.basePoint * (if (GameLevel.NOVICE.questionTime - gameState.answerTime > 0) (GameLevel.NOVICE.questionTime - gameState.answerTime) + GameLevel.NOVICE.basePoint else GameLevel.NOVICE.basePoint)
    }
    GameLevel.NOT_SO_BAD -> {
        GameLevel.NOT_SO_BAD.basePoint * (if (GameLevel.NOT_SO_BAD.questionTime - gameState.answerTime > 0) (GameLevel.NOT_SO_BAD.questionTime - gameState.answerTime) + GameLevel.NOT_SO_BAD.basePoint else GameLevel.NOT_SO_BAD.basePoint)
    }
    GameLevel.PRO -> {
        GameLevel.PRO.basePoint * (if (GameLevel.PRO.questionTime - gameState.answerTime > 0) (GameLevel.PRO.questionTime - gameState.answerTime) + GameLevel.PRO.basePoint else GameLevel.PRO.basePoint)
    }
}
