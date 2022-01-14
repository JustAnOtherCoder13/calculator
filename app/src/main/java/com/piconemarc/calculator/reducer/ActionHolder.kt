package com.piconemarc.calculator.reducer

import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.UiAction

sealed class GlobalAction : UiAction{
    data class UpdateHomeState(val baseAction: UiAction):GlobalAction()
    data class UpdateGameState(val baseAction : UiAction) : GlobalAction()
    data class UpdateGameOverState(val baseAction: UiAction) : GlobalAction()
    data class UpdateTopTenState(val baseAction: UiAction) : GlobalAction()
}

sealed class HomeAction : UiAction {
    data class UpdateTableList (val tableNumber : Int, val isChecked : Boolean, val tableCheckedList : MutableList<Int>) : HomeAction()
    data class UpdateOperandList(val operand : String, val isChecked : Boolean,val selectedOperandList : MutableList<String>) : HomeAction()
    data class UpdateGameLevel(val gameLevel : GameLevel) : HomeAction()
}

sealed class GameAction : UiAction {
    data class StartGame(val gameParameters: GameParameters) : GameAction()
    data class UpdateRemainingTime ( val remainingTime : Long) : GameAction()
    object UpdateGoodAnswerChainCount : GameAction()
    object UpdateBonus : GameAction()
    object UpdateScore : GameAction()
    data class UpdateOperation(
        val gameParameters: GameParameters,
        val questionCounter : Int
        ) : GameAction()
    data class UpdateResult (val result : String) : GameAction()
}