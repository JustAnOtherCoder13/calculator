package com.piconemarc.calculator.reducer

import com.piconemarc.calculator.reducer.screenReducer.homeReducer
import com.piconemarc.calculator.utils.interfaces.Reducer
import com.piconemarc.calculator.utils.interfaces.StoreSubscriber

val appReducer : Reducer<GlobalState> = {
    old, action ->
    action as GlobalAction
    when(action){
        is GlobalAction.UpdateHomeState -> {
            old.copy(homeState = homeReducer(
                old.homeState,
                action.baseAction
            ) )
        }
        else -> old
    }
}

val appStoreSubscriber : StoreSubscriber<GlobalState> = {
    globalState ->
    homeState_.value = globalState.homeState
    gameState_.value = globalState.gameState
    gameOverState_.value = globalState.gameOverState
    topTenState_.value = globalState.topTenState
}