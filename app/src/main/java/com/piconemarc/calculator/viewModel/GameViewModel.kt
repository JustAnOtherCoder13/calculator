package com.piconemarc.calculator.viewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.piconemarc.calculator.reducer.*
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.initCountDownTimer
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
            is GameAction.StartAnswerChrono -> {
                initCountDownTimer(
                    allocatedTime = (action.gameState.gameParameters.gameLevel.questionTime*1000).toLong(),
                    onTick_ = {
                              this.dispatchAction(
                                  GameAction.UpdateAnswerChrono((it/1000).toInt())
                              )
                    },
                    onFinish_ = {
                        when(action.gameState.gameParameters.gameLevel){
                            GameLevel.PRO->{
                                this.dispatchAction(
                                    GameAction.SubmitResult(
                                        gameState = action.gameState,
                                        result = "",
                                        doOnSuccess = {
                                            this.dispatchAction(
                                                GameAction.UpdateScore(
                                                    gameState = gameState,
                                                    result = ""
                                                )
                                            )
                                        }
                                    )
                                )
                            }
                            else -> {}
                        }
                    }
                )
            }

            is GameAction.SubmitResult -> {
                    this.dispatchAction(
                        GameAction.UpdateGoodAnswerChainCount(action.gameState, action.result),
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
                    GameAction.StartAnswerChrono(action.gameState)
                )
            }
            else -> {

            }
        }
    }
}

