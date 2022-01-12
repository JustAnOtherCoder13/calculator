package com.piconemarc.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piconemarc.calculator.navigation.NavDestinations
import com.piconemarc.calculator.ui.screen.HomeScreen
import com.piconemarc.calculator.ui.theme.CalculatorTheme
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
                        HomeScreen(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(route = NavDestinations.GameScreen.getRoute()){
                        //TODO implement game screen
                    }
                }

            }
        }
    }
}