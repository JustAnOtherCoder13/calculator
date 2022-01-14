package com.piconemarc.calculator.viewModel

import androidx.compose.runtime.getValue
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
        when (action) {
            is GameAction.UpdateScore -> {
                this.dispatchAction(
                    GameAction.UpdateOperation(
                        action.oldState
                    )
                )
            }
            else -> {

            }
        }
    }
}

fun String.isOperationTrue(operand: String, y: String, submittedResult: String): Boolean {
    val firstNumber: Int = this.trim().toInt()
    val secondNumber = y.trim().toInt()
    val operationResult =
        if (submittedResult.trim().isNotEmpty()) submittedResult.trim().toInt() else 0

    return when (operand) {
        "+" -> firstNumber + secondNumber == operationResult
        "-" -> firstNumber - secondNumber == operationResult
        "*" -> firstNumber * secondNumber == operationResult
        else -> {
            false
        }
    }
}