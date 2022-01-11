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
    data class UpdateTableList (val tableList : List<Int>) : HomeAction()
    data class UpdateOperandList(val operandList : List<String>) : HomeAction()
    data class UpdateGameLevel(val gameLevel : GameLevel) : HomeAction()
    data class StartNewGame(val gameParameters: GameParameters) : HomeAction()
}

sealed class GameAction : UiAction {
    data class UpdateRemainingTime ( val remainingTime : Long) : GameAction()
    object UpdateQuestionCount : GameAction()
    object UpdateGoodAnswerChainCount : GameAction()
    object UpdateBonus : GameAction()
    object UpdateScore : GameAction()
    object UpdateOperation : GameAction()
    data class UpdateResult (val result : String) : GameAction()
}