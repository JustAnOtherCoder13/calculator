package com.piconemarc.calculator.reducer

import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.model.ui.TopPlayer
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.UiState
import kotlinx.coroutines.flow.MutableStateFlow

data class GlobalState(
    val homeState: HomeState,
    val gameState: GameState,
    val gameOverState: GameOverState,
    val topTenState: TopTenState
) : UiState

data class HomeState(
    val gameParams: GameParameters = GameParameters(listOf(), listOf(), GameLevel.NOVICE),
    val tableList: List<Int> = listOf(),
    val operandList : List<String> = listOf(),
    val gameLevel : GameLevel = GameLevel.NOVICE
) : UiState

data class GameOverState(
    val score: Long = 0L,
    val questionNumber: Int = 0,
    val goodAnswer: Int = 0,
    val bestGoodAnswerChain: Int = 0
) : UiState

data class GameState(
    val remainingTime: Long = 0L,
    val questionCounter: Int = 0,
    val goodAnswerChain: Int = 0,
    val bonus: String = "",
    val score: Long = 0L,
    val firstNumber: Int = 0,
    val operand: String = "",
    val secondNumber: Int = 0,
    val result: String = ""
) : UiState

data class TopTenState(
    val topTenList: List<TopPlayer> = listOf()
) : UiState

//Encapsulate each screen state in Mutable state-------------------------------------
val homeState_ : MutableStateFlow<HomeState> = MutableStateFlow(
    HomeState()
)

val gameState_ : MutableStateFlow<GameState> = MutableStateFlow(
    GameState()
)

val gameOverState_ : MutableStateFlow<GameOverState> = MutableStateFlow(
    GameOverState()
)

val topTenState_ : MutableStateFlow<TopTenState> = MutableStateFlow(
    TopTenState()
)