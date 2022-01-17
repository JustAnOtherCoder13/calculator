package com.piconemarc.calculator.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.calculator.reducer.*
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        when (action) {
            //todo change chrono here
            is GameAction.StartAnswerChrono -> {
                viewModelScope.launch {
                    for (t in 0..100) {
                        this@GameViewModel.dispatchAction(
                            GameAction.UpdateAnswerChrono(t)
                        )
                        delay(1000)
                    }
                }
            }

            is GameAction.SubmitResult -> {
                    this.dispatchAction(
                        GameAction.UpdateGoodAnswerChainCount(action.gameState),
                    )
                    action.doOnSuccess()
            }

            is GameAction.UpdateScore -> {
                this.dispatchAction(
                    GameAction.UpdateOperation(
                        action.gameState
                    )
                )
                this.dispatchAction(
                    GameAction.StartAnswerChrono
                )
            }
            else -> {

            }
        }
    }
}

fun Int.isOperationTrue(operand: String, y: Int, submittedResult: String): Boolean {
    val operationResult =
        if (submittedResult.trim().isNotEmpty()) submittedResult.trim().toInt() else 0

    return when (operand) {
        "+" -> this + y == operationResult
        "-" -> this - y == operationResult
        "*" -> this * y == operationResult
        else -> {
            false
        }
    }
}