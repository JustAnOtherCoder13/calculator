package com.piconemarc.calculator.reducer.screenReducer

import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.utils.*
import com.piconemarc.calculator.utils.interfaces.Reducer
import kotlin.random.Random

val gameReducer: Reducer<GameState> = { old, action ->
    action as GameAction
    when (action) {
        is GameAction.StartGame -> {
            val firstNumber = action.gameParameters.tableList.random()
            val operand = action.gameParameters.operandList.random()
            val secondNumber = Random.nextInt(1, 10)

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
                noviceAnswerValues = getNoviceAnswerValueList(
                    goodAnswer = getGoodAnswerToString(operand, firstNumber, secondNumber)
                )
            )
        }

        is GameAction.UpdateOperation -> {
            val firstNumber = action.gameState.gameParameters.tableList.random()
            val operand = action.gameState.gameParameters.operandList.random()
            val secondNumber = Random.nextInt(1, 10)

            old.copy(
                firstNumber = firstNumber,
                operand = operand,
                secondNumber = secondNumber,
                questionCounter = action.gameState.questionCounter + 1,
                result = "",
                noviceAnswerValues = getNoviceAnswerValueList(
                    goodAnswer = getGoodAnswerToString(operand, firstNumber, secondNumber)
                )
            )
        }

        is GameAction.UpdateGoodAnswerChainCount -> {
            val goodAnswerChainCount = getUpdatedGoodAnswerChain(action)
            old.copy(
                goodAnswerChain = goodAnswerChainCount,
                bonus = getCalculatedBonus(goodAnswerChainCount),
                bestGoodAnswerChain = rememberBestGoodAnswerChain(goodAnswerChainCount, old)
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
                score = getNewScore(action, old),
                goodAnswerCount = getUpdatedGoodAnswerCount(action, old)
            )
        }
        is GameAction.SubmitResult -> old.copy(
            result = action.result
        )
        is GameAction.StartAnswerChrono -> old
        is GameAction.UpdateAnswerChrono -> old.copy(
            answerTime = action.time
        )
    }
}