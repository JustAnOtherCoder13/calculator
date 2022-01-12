package com.piconemarc.calculator.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.viewModel.GameViewModel


@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    gameParams : GameParameters
){
    Text(text = gameParams.gameLevel.value)
}