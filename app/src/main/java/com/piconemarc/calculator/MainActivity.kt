package com.piconemarc.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.navigation.NavDestinations
import com.piconemarc.calculator.ui.screen.GameScreen
import com.piconemarc.calculator.ui.screen.HomeScreen
import com.piconemarc.calculator.ui.theme.CalculatorTheme
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
                    composable(route = NavDestinations.Home.getRoute()) {
                        val homeViewModel = hiltViewModel<HomeViewModel>()

                        DisposableEffect(key1 = homeViewModel){
                            onDispose {
                                //todo reset game parameters
                            }
                        }
                        HomeScreen(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(route = NavDestinations.GameScreen.getRoute()){
                        val gameViewModel = hiltViewModel<GameViewModel>()
                        GameScreen(
                            navController = navController,
                            gameViewModel = gameViewModel,
                            gameParams = Gson().fromJson(it.arguments?.getString(NavDestinations.GameScreen.key), GameParameters::class.java)
                        )
                    }
                }

            }
        }
    }
}