package com.piconemarc.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
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
        val homeViewModel by viewModels<HomeViewModel>()

        setContent {
            val navController = rememberNavController()

            CalculatorTheme {
                HomeScreen(
                    navController = navController,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}