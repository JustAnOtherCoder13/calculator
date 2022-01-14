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
        Title()
        Timer()
        Score(
            goodAnswerChain = gameViewModel.gameState.goodAnswerChain.toString(),
            score = gameViewModel.gameState.score.toString()
        )
        Answer(
            firstNumber = gameViewModel.gameState.firstNumber.toString(),
            operand = gameViewModel.gameState.operand,
            secondNumber = gameViewModel.gameState.secondNumber.toString(),
            resultValue = gameViewModel.gameState.result,
            onResultChange = { result ->
                gameViewModel.dispatchAction(
                    GameAction.UpdateResult(result)
                )
            },
            onValidateResult = {
                gameViewModel.dispatchAction(
                    GameAction.UpdateGoodAnswerChainCount(
                        gameViewModel.gameState.goodAnswerChain,
                        gameViewModel.gameState.result,
                        gameViewModel.gameState.firstNumber.toString(),
                        gameViewModel.gameState.secondNumber.toString(),
                        gameViewModel.gameState.operand
                    )
                )
                gameViewModel.dispatchAction(
                    GameAction.UpdateScore(
                        gameViewModel.gameState.score,
                        gameViewModel.gameState.bonus,
                        gameViewModel.gameState.result,
                        gameViewModel.gameState.firstNumber.toString(),
                        gameViewModel.gameState.secondNumber.toString(),
                        gameViewModel.gameState.operand,
                        gameParams,
                        gameViewModel.gameState.questionCounter,
                        gameViewModel.gameState
                    )
                )

            },
        )

    }
}

@Composable
private fun Answer(
    firstNumber: String,
    operand: String,
    secondNumber: String,
    resultValue: String,
    onResultChange: (result: String) -> Unit,
    onValidateResult: () -> Unit

) {
    GreenOutlinedColumn(
        padding = PaddingValues(
            top = ScoreMarge,
            start = LittleMarge,
            end = LittleMarge
        )
    ) {
        Row() {
            Text(text = firstNumber, style = BigFontTextStyle)
            Text(text = " $operand ", style = BigFontTextStyle)
            Text(text = secondNumber, style = BigFontTextStyle)
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
            value = resultValue,
            onValueChange = onResultChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = { Text(text = "Result") }

        )
        BigButton(
            onButtonClick = onValidateResult,
            buttonText = "Next"
        )
    }
}

@Composable
private fun Score(
    goodAnswerChain: String,
    score: String
) {
    GreenOutlinedColumn {
        Row() {
            Text(text = "Chain : ", style = MaterialTheme.typography.h3)
            Text(
                text = goodAnswerChain,
                style = MaterialTheme.typography.h3
            )
        }
        Row() {
            Text(text = "Score : ", style = LittleBigFontTextStyle)
            Text(text = score, style = LittleBigFontTextStyle)
        }
    }
}

@Composable
private fun Timer() {
    GreenOutlinedColumn {
        Text(text = "Time", style = MaterialTheme.typography.h2)
        Row() {
            Text(text = "00", style = LittleBigFontTextStyle)
            Text(text = " : ", style = LittleBigFontTextStyle)
            Text(text = "00", style = LittleBigFontTextStyle)
        }
    }
}

@Composable
private fun Title() {
    Text(text = "Start", style = MaterialTheme.typography.h1)
}