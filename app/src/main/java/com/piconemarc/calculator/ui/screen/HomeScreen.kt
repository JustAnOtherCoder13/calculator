package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piconemarc.calculator.R
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.ui.common.GameLevelRadioButton
import com.piconemarc.calculator.ui.common.BaseToggleButton
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.BigMarge
import com.piconemarc.calculator.ui.theme.RegularMarge
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.operandList
import com.piconemarc.calculator.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HomeTitle()
        TableList(
            onTableListChange = { tableNumber, isChecked ->
                homeViewModel.dispatchAction(
                    HomeAction.UpdateTableList(
                        tableNumber,
                        isChecked,
                        homeViewModel.homeState.checkedTableList.toMutableList()
                    )
                )
            }
        )
        OperandList(
            onOperandListChange = { operand, isChecked ->
                homeViewModel.dispatchAction(
                    HomeAction.UpdateOperandList(
                        operand,
                        isChecked,
                        homeViewModel.homeState.operandList.toMutableList()
                    )
                )
            }
        )
        GameLevel(
            onGameLevelChange = { gameLevel ->
                homeViewModel.dispatchAction(
                    HomeAction.UpdateGameLevel(gameLevel)
                )
            },
            selectedLevel = homeViewModel.homeState.gameLevel
        )
        StartGameButton(onStartButtonClicked = {
            if (
                homeViewModel.homeState.checkedTableList.isNotEmpty()
                && homeViewModel.homeState.operandList.isNotEmpty()
            ) {
                homeViewModel.dispatchAction(
                    HomeAction.StartNewGame(
                        GameParameters(
                            homeViewModel.homeState.checkedTableList,
                            homeViewModel.homeState.operandList,
                            homeViewModel.homeState.gameLevel
                        )
                    )
                )
            }
        })
    }
}

@Composable
fun HomeTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier.padding(vertical = RegularMarge),
            text = stringResource(R.string.HomeTitle),
            style = MaterialTheme.typography.h1
        )
    }
}


@Composable
fun TableList(
    onTableListChange: (tableNumber: Int, isChecked: Boolean) -> Unit,
) {
    GreenOutlinedColumn {
        Text(
            text = stringResource(R.string.tableSelectionTitle),
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier = Modifier
                .padding(vertical = RegularMarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 1..10) {
                BaseToggleButton(
                    buttonText = i,
                    onChecked = onTableListChange
                )
            }
        }
    }
}

@Composable
fun OperandList(
    onOperandListChange: (operand: String, isChecked: Boolean) -> Unit
) {
    GreenOutlinedColumn {
        Text(
            text = stringResource(R.string.chooseOperationTitle),
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier = Modifier
                .width(150.dp)
                .padding(vertical = RegularMarge),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (o in operandList) {
                BaseToggleButton(
                    buttonText = o,
                    onChecked = onOperandListChange
                )
            }
        }
    }
}

@Composable
private fun GameLevel(
    onGameLevelChange: (gameLevel: GameLevel) -> Unit,
    selectedLevel: GameLevel
) {
    GreenOutlinedColumn {
        Text(
            text = stringResource(R.string.chooseLevelTitle),
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier = Modifier
                .padding(vertical = RegularMarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (g in GameLevel.values()) {
                GameLevelRadioButton(
                    gameLevel = g,
                    onChecked = { gameLevel -> onGameLevelChange(gameLevel) },
                    isChecked = g.value == selectedLevel.value
                )
            }
        }
    }
}

@Composable
fun StartGameButton(onStartButtonClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onStartButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BigMarge, vertical = RegularMarge)
                .height(100.dp)
        ) {
            Text(
                text = stringResource(R.string.startGameButtonTitle),
                style = MaterialTheme.typography.h3
            )
        }
    }

}
