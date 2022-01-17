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
    data class UpdateTableList (val tableNumber : Int, val isChecked : Boolean, val homeState: HomeState) : HomeAction()
    data class UpdateOperandList(val operand : String, val isChecked : Boolean,val homeState: HomeState) : HomeAction()
    data class UpdateGameLevel(val gameLevel : GameLevel) : HomeAction()
}

sealed class GameAction : UiAction {
    data class StartGame(val gameParameters: GameParameters) : GameAction()
    data class UpdateRemainingTime ( val remainingTime : Long) : GameAction()
    data class SubmitResult(val gameState: GameState, val result: String,val doOnSuccess:()->Unit) : GameAction()
    data class UpdateGoodAnswerChainCount(val gameState: GameState,val result: String) : GameAction()
    data class UpdateScore(val gameState: GameState, val result: String) : GameAction()
    data class UpdateOperation(val gameState: GameState) : GameAction()
    data class UpdateResult (val result : String) : GameAction()
    data class StartAnswerChrono(val gameState: GameState) : GameAction()
    data class UpdateAnswerChrono(val time : Int) : GameAction()
}