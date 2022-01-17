package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.piconemarc.calculator.R
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.navigation.NavDestinations
import com.piconemarc.calculator.reducer.HomeAction
import com.piconemarc.calculator.reducer.HomeState
import com.piconemarc.calculator.ui.common.BaseToggleButton
import com.piconemarc.calculator.ui.common.GameLevelRadioButton
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.BigMarge
import com.piconemarc.calculator.ui.theme.RegularMarge
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.NavDestination
import com.piconemarc.calculator.utils.operandList

@Composable
fun HomeScreen(
    homeState: HomeState,
    onHomeEvent : (homeAction : HomeAction)-> Unit,
    onNavEvent : (navDestination : NavDestination, arg : String)-> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HomeTitle()
        TableList(
            onTableListChange = { tableNumber, isChecked ->
                onHomeEvent(
                    HomeAction.UpdateTableList(
                        tableNumber,
                        isChecked,
                        homeState
                    )
                )
            }
        )
        OperandList(
            onOperandListChange = { operand, isChecked ->
                onHomeEvent(
                    HomeAction.UpdateOperandList(
                        operand,
                        isChecked,
                        homeState
                    )
                )
            }
        )
        GameLevel(
            onGameLevelChange = { gameLevel ->
                onHomeEvent(
                    HomeAction.UpdateGameLevel(gameLevel)
                )
            },
            selectedLevel = homeState.gameLevel
        )
        BigButton(onButtonClick = {
            if (
                homeState.checkedTableList.isNotEmpty()
                && homeState.operandList.isNotEmpty()
            ) {
                onNavEvent(
                    NavDestinations.GameScreen,
                    Gson().toJson(
                        GameParameters().build(homeState)
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
fun BigButton(
    buttonText : String = stringResource(R.string.startGameButtonTitle),
    onButtonClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BigMarge, vertical = RegularMarge)
                .height(100.dp)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.h3
            )
        }
    }
}