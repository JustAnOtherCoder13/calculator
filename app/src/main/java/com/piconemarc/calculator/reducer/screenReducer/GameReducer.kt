package com.piconemarc.calculator.reducer.screenReducer

import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.Reducer
import com.piconemarc.calculator.viewModel.isOperationTrue
import kotlin.random.Random

val gameReducer: Reducer<GameState> = { old, action ->
    action as GameAction
    when (action) {
        is GameAction.StartGame -> old.copy(
            firstNumber = action.gameParameters.tableList.random(),
            operand = action.gameParameters.operandList.random(),
            secondNumber = Random.nextInt(1, 10),
            questionCounter = 0,
            result = "",
            score = 0,
            bonus = 1,
            goodAnswerChain = 0,
            gameParameters = action.gameParameters
        )

        is GameAction.UpdateOperation -> old.copy(
            firstNumber = action.gameState.gameParameters.tableList.random(),
            operand = action.gameState.gameParameters.operandList.random(),
            secondNumber = Random.nextInt(1, 10),
            questionCounter = action.gameState.questionCounter + 1,
            result = ""
        )

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
