package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
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
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.BigFontTextStyle
import com.piconemarc.calculator.ui.theme.LittleBigFontTextStyle
import com.piconemarc.calculator.ui.theme.LittleMarge
import com.piconemarc.calculator.ui.theme.ScoreMarge
import com.piconemarc.calculator.utils.interfaces.NavDestination


@Composable
fun GameScreen(
    gameState: GameState,
    onGameEvent: (gameAction: GameAction) -> Unit,
    onNavEvent: (navDestination: NavDestination, arg: String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Title()
        Timer(gameState.remainingTime)
        Score(
            goodAnswerChain = gameState.goodAnswerChain.toString(),
            score = gameState.score.toString()
        )
        Answer(
            firstNumber = gameState.firstNumber.toString(),
            operand = gameState.operand,
            secondNumber = gameState.secondNumber.toString(),
            resultValue = gameState.result,
            onResultChange = { result ->
                onGameEvent(GameAction.UpdateResult(result))
            },
            onValidateResult = {
                onGameEvent(GameAction.SubmitResult(gameState, doOnSuccess = {
                    onGameEvent(GameAction.UpdateScore(gameState))
                }))
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
private fun Timer(remainingTime: Long) {
    GreenOutlinedColumn {
        Text(text = "Time", style = MaterialTheme.typography.h2)
        Row() {
            Text(text = "$remainingTime", style = LittleBigFontTextStyle)
            //Text(text = " : ", style = LittleBigFontTextStyle)
            //Text(text = "00", style = LittleBigFontTextStyle)
        }
    }
}

@Composable
private fun Title() {
    Text(text = "Start", style = MaterialTheme.typography.h1)
}