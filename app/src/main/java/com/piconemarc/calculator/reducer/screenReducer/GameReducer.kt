package com.piconemarc.calculator.reducer.screenReducer

import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.utils.interfaces.Reducer
import kotlin.random.Random

val gameReducer : Reducer<GameState> = {
    old, action ->
    action as GameAction
    when(action){
        is GameAction.StartGame -> old.copy(
            firstNumber = action.gameParameters.tableList.random(),
            operand = action.gameParameters.operandList.random(),
            secondNumber = Random.nextInt(1, 10),
            questionCounter = 0,
            result = "",
            score = 0,
            goodAnswerChain = 0
        )
        is GameAction.UpdateOperation->old.copy(
            firstNumber = action.gameParameters.tableList.random(),
            operand = action.gameParameters.operandList.random(),
            secondNumber = Random.nextInt(1, 10),
            questionCounter = action.questionCounter + 1
        )
        is GameAction.UpdateBonus -> old
        is GameAction.UpdateGoodAnswerChainCount -> old
        is GameAction.UpdateRemainingTime -> old
        is GameAction.UpdateResult -> old.copy(
            result = action.result
        )
        is GameAction.UpdateScore -> old
    }
}