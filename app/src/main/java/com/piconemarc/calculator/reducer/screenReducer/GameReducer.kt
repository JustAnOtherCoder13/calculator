package com.piconemarc.calculator.reducer.screenReducer

import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.utils.interfaces.Reducer

val gameReducer:Reducer<GameState> = {
    old, action ->
    action as GameAction
    when(action){
        is GameAction.UpdateOperation->old.copy(

        )
        is GameAction.UpdateBonus -> old
        is GameAction.UpdateGoodAnswerChainCount -> old
        is GameAction.UpdateQuestionCount-> old
        is GameAction.UpdateRemainingTime -> old
        is GameAction.UpdateResult -> old
        is GameAction.UpdateScore -> old
    }
}