package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piconemarc.calculator.R
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.ui.common.BaseTableToggleButton
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.RegularMarge
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.operandList
import com.piconemarc.calculator.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Column {
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
        GameLevel(onGameLevelChange = { gameLevel ->
            homeViewModel.dispatchAction(
                HomeAction.UpdateGameLevel(gameLevel)
            )
        })
        StartGameButton(onStartButtonClicked = {
            if (
                homeViewModel.homeState.checkedTableList.isNotEmpty()
                && homeViewModel.homeState.operandList.isNotEmpty()
            )
                homeViewModel.dispatchAction(
                    HomeAction.StartNewGame(
                        GameParameters(
                            homeViewModel.homeState.checkedTableList,
                            homeViewModel.homeState.operandList,
                            homeViewModel.homeState.gameLevel
                        )
                    )
                )
        })
    }
}

@Composable
fun HomeTitle() {
    Text(
        modifier = Modifier.padding(vertical = RegularMarge),
        text = stringResource(R.string.HomeTitle),
        style = MaterialTheme.typography.h1
    )
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
                BaseTableToggleButton(
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
                BaseTableToggleButton(
                    buttonText = o,
                    onChecked = onOperandListChange
                )
            }
        }
    }
}

@Composable
private fun GameLevel(
    onGameLevelChange: (gameLevel: GameLevel) -> Unit
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
            for (s in GameLevel.values()) {
                BaseTableToggleButton(
                    buttonText = s.value,
                    //Todo have to optimize here
                    onChecked = { buttonText, isChecked ->
                        when (buttonText) {
                            GameLevel.NOVICE.value -> {
                                onGameLevelChange(GameLevel.NOVICE)
                            }
                            GameLevel.NOT_SO_BAD.value -> {
                                onGameLevelChange(GameLevel.NOT_SO_BAD)
                            }
                            GameLevel.PRO.value -> {
                                onGameLevelChange(GameLevel.PRO)
                            }
                        }
                    },
                    width = Dp.Unspecified
                )
            }
        }
    }
}

@Composable
fun StartGameButton(onStartButtonClicked: () -> Unit) {
    Button(onClick = onStartButtonClicked) {
        Text(
            text = stringResource(R.string.startGameButtonTitle),
            style = MaterialTheme.typography.h3
        )
    }
}
