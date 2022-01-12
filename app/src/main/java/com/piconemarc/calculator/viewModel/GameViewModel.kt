package com.piconemarc.calculator.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piconemarc.calculator.reducer.*
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    store: DefaultStore<GlobalState>
) : BaseViewModel<GameAction, GameState>(
    store,
    gameState_
) {
    val gameState by uiState

    init {
        viewModelScope.launch { state.collectLatest { uiState.value = it } }
    }

    override fun dispatchAction(action: GameAction) {
        updateState(GlobalAction.UpdateGameState(action))
    }


}