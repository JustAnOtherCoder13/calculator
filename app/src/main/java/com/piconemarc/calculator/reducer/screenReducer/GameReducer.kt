package com.piconemarc.calculator.reducer.screenReducer

import android.util.Log
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
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
            goodAnswerChain = 0,
            gameParameters = action.gameParameters
        )

        is GameAction.UpdateOperation -> old.copy(
            firstNumber = action.gameState.gameParameters.tableList.random(),
            operand = action.gameState.gameParameters.operandList.random(),
            secondNumber = Random.nextInt(1, 10),
            questionCounter = action.gameState.questionCounter + 1,
        )

        is GameAction.UpdateGoodAnswerChainCount -> {
            val goodAnswerChainCount = if (action.firstNumber.isOperationTrue(
                    action.operand,
                    action.secondNumber,
                    action.result
                )
            ) action.oldCount + 1 else 0
            old.copy(
                goodAnswerChain = goodAnswerChainCount,
                bonus = when (goodAnswerChainCount) {
                    3,4 -> 300

                    5,6 -> 600

                    7,8,9 -> 1000

                    else -> {
                        if (goodAnswerChainCount > 10) 2000
                        else 1
                    }
                }
            )
        }
        is GameAction.UpdateRemainingTime -> old
        is GameAction.UpdateResult -> old.copy(
            result = action.result
        )
        is GameAction.UpdateScore -> {
            old.copy(
                score = if (action.firstNumber.isOperationTrue(
                        action.operand,
                        action.secondNumber,
                        action.result
                    )
                ) {
                    action.oldScore + (1 * action.bonus)
                } else action.oldScore,
                result = ""
            )
        }
        is GameAction.TimesUp -> old
    }
}