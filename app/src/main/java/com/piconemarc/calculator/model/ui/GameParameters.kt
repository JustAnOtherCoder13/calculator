package com.piconemarc.calculator.model.ui

import com.piconemarc.calculator.reducer.HomeState
import com.piconemarc.calculator.utils.GameLevel

data class GameParameters(
    val tableList : List<Int> = listOf(),
    val operandList : List<String> = listOf(),
    val gameLevel : GameLevel = GameLevel.NOVICE
){
    fun build(homeState: HomeState):GameParameters = this.copy(
        tableList = homeState.checkedTableList,
        operandList = homeState.operandList,
        gameLevel = homeState.gameLevel
    )
}

