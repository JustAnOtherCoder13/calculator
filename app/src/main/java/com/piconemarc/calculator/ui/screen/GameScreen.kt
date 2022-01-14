package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piconemarc.calculator.model.ui.GameParameters
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.*
import com.piconemarc.calculator.viewModel.GameViewModel


@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    gameParams: GameParameters
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Start", style = MaterialTheme.typography.h1)
        GreenOutlinedColumn {
            Text(text = "Time", style = MaterialTheme.typography.h2)
            Row() {
                Text(text = "00", style = LittleBigFontTextStyle)
                Text(text = " : ", style = LittleBigFontTextStyle)
                Text(text = "00", style = LittleBigFontTextStyle)
            }
        }
        GreenOutlinedColumn {
            Row() {
                Text(text = "Chain : ", style = MaterialTheme.typography.h3)
                Text(text = gameViewModel.gameState.goodAnswerChain.toString(), style = MaterialTheme.typography.h3)
            }
            Row() {
                Text(text = "Score : ", style = LittleBigFontTextStyle)
                Text(text = gameViewModel.gameState.score.toString(), style = LittleBigFontTextStyle)
            }
        }
        GreenOutlinedColumn(padding = PaddingValues(top = ScoreMarge, start = LittleMarge, end = LittleMarge)) {
            Row() {
                Text(text = gameViewModel.gameState.firstNumber.toString(), style = BigFontTextStyle)
                Text(text = " ${gameViewModel.gameState.operand} ", style = BigFontTextStyle)
                Text(text = gameViewModel.gameState.secondNumber.toString(), style = BigFontTextStyle)
                Text(text = " =", style = BigFontTextStyle)
            }
            TextField(
                textStyle = BigFontTextStyle,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onPrimary,
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                ),
                modifier = Modifier
                    .width(200.dp),
                value =  gameViewModel.gameState.result,
                onValueChange = {result ->
                    gameViewModel.dispatchAction(
                        GameAction.UpdateResult(result)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(text = "Result") }

            )
            BigButton (
                onButtonClick = {
                                gameViewModel.dispatchAction(
                                    GameAction.UpdateOperation
                                )
                },
                buttonText = "Next"
            )
        }

    }
}