package com.piconemarc.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.model.ui.PlayedGameInfo
import com.piconemarc.calculator.navigation.NavDestinations
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.ui.screen.GameOverScreen
import com.piconemarc.calculator.ui.screen.GameScreen
import com.piconemarc.calculator.ui.screen.HomeScreen
import com.piconemarc.calculator.ui.theme.CalculatorTheme
import com.piconemarc.calculator.utils.initCountDownTimer
import com.piconemarc.calculator.viewModel.GameViewModel
import com.piconemarc.calculator.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CalculatorTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavDestinations.Home.destination
                ) {
                    //------------------------------------------------------HOME-------------

                    composable(route = NavDestinations.Home.getRoute()) {
                        val homeViewModel = hiltViewModel<HomeViewModel>()

                        DisposableEffect(key1 = homeViewModel) {
                            onDispose {
                                //todo reset game parameters
                            }
                        }
                        HomeScreen(
                            homeState = homeViewModel.homeState,
                            onHomeEvent = { homeAction -> homeViewModel.dispatchAction(homeAction) },
                            onNavEvent = { navDestination, arg ->
                                navDestination.doNavigation(
                                    navController,
                                    arg
                                )
                            }
                        )
                    }

                    //------------------------------------------------------GAME-------------

                    composable(route = NavDestinations.GameScreen.getRoute()) {
                        val gameViewModel = hiltViewModel<GameViewModel>()
                        val gameParameters = Gson().fromJson(
                            it.arguments?.getString(NavDestinations.GameScreen.key),
                            GameParameters::class.java
                        )
                        LaunchedEffect(key1 = gameViewModel) {
                            gameViewModel.dispatchAction(
                                GameAction.StartGame(gameParameters = gameParameters)
                            )
                            gameViewModel.dispatchAction(
                                GameAction.StartAnswerChrono(gameState = gameViewModel.gameState)
                            )
                            initCountDownTimer(
                                allocatedTime = gameParameters.gameLevel.allocatedTime,
                                onTick_ = { remainingTime ->
                                    gameViewModel.dispatchAction(
                                        GameAction.UpdateRemainingTime(remainingTime = remainingTime)
                                    )
                                },
                                onFinish_ = {
                                    NavDestinations.GameOverScreen.doNavigation(
                                        navController,
                                        Gson().toJson(
                                            PlayedGameInfo(
                                                score = gameViewModel.gameState.score,
                                                answeredQuestions = gameViewModel.gameState.questionCounter,
                                                goodAnswer = gameViewModel.gameState.goodAnswerCount,
                                                bestGoodAnswerChain = gameViewModel.gameState.bestGoodAnswerChain,
                                                answerPerTime = "1 answer per ${(gameViewModel.gameState.gameParameters.gameLevel.allocatedTime/1000)/gameViewModel.gameState.questionCounter} second"
                                            )
                                        )
                                    )
                                }
                            )
                        }
                        GameScreen(
                            gameState = gameViewModel.gameState,
                            onGameEvent = { gameAction -> gameViewModel.dispatchAction(action = gameAction) },
                        )
                    }

                    //------------------------------------------------------GAME OVER-------------

                    composable(route = NavDestinations.GameOverScreen.getRoute()) {
                        val playedGameInfo = Gson().fromJson(
                            it.arguments?.getString(NavDestinations.GameOverScreen.key),
                            PlayedGameInfo::class.java
                        )
                        GameOverScreen(playedGameInfo = playedGameInfo)
                    }
                }

            }
        }
    }
}