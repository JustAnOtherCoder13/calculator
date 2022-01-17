package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.piconemarc.calculator.ui.theme.*
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.interfaces.NavDestination
import kotlin.random.Random


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
            gameLevel = gameState.gameParameters.gameLevel,
            goodAnswer = gameState.goodAnswer,
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
    gameLevel: GameLevel,
    goodAnswer: String,
    onValidateResult: () -> Unit,

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
        when (gameLevel) {
            GameLevel.NOVICE -> {
                ResultSelector(
                    goodAnswer,
                    onResultChange,
                    onValidateResult
                )
            }
            else -> {
                ResultTextField(
                    resultValue,
                    onResultChange,
                    onValidateResult
                )
            }
        }
    }
}

@Composable
private fun ResultSelector(
    goodAnswer: String,
    onResultChange: (result: String) -> Unit,
    onValidateResult: () -> Unit
) {
    val randomAnswerPosition = Random.nextInt(0,2)

    Text(text = "Choose your answer :", style = LittleBigFontTextStyle)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0..3) {
            when (i) {
                randomAnswerPosition -> {
                    NoviceAnswerButton(
                        value = goodAnswer,
                        onButtonClick = {
                            onResultChange(it)
                            onValidateResult()
                        }
                    )
                }
                else -> {
                    RandomNoviceAnswerButton(
                        goodAnswer = goodAnswer,
                        onButtonClick = {
                            onResultChange(it)
                            onValidateResult()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RandomNoviceAnswerButton(
    goodAnswer: String,
    onButtonClick: (value: String) -> Unit
) {
    val randomModifierBound =
        if (goodAnswer.toInt() > 1) goodAnswer.toInt() else Random.nextInt(3, 10)
    val randomValueModifier = Random.nextInt(1, randomModifierBound)
    val value = if (goodAnswer.toInt() - randomValueModifier < 0) {
        goodAnswer.toInt() + randomValueModifier
    } else {
        goodAnswer.toInt() - randomValueModifier
    }
    NoviceAnswerButton(
        value = value.toString(),
        onButtonClick = onButtonClick
    )
}

@Composable
private fun NoviceAnswerButton(
    value: String,
    onButtonClick: (value: String) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {
                onButtonClick(value)
            }
            .size(100.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
            .border(
                width = ThinBorder,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            ),

        contentAlignment = Alignment.Center
    ) {
        Text(text = value, style = MaterialTheme.typography.h1)
    }
}

@Composable
private fun ResultTextField(
    resultValue: String,
    onResultChange: (result: String) -> Unit,
    onValidateResult: () -> Unit
) {
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
            Text(text = "Score : ", style = MediumBigFontTextStyle)
            Text(text = score, style = MediumBigFontTextStyle)
        }
    }
}

@Composable
private fun Timer(remainingTime: Long) {
    GreenOutlinedColumn {
        Text(text = "Time", style = MaterialTheme.typography.h2)
        Row() {
            Text(text = "$remainingTime", style = MediumBigFontTextStyle)
            //Text(text = " : ", style = LittleBigFontTextStyle)
            //Text(text = "00", style = LittleBigFontTextStyle)
        }
    }
}

@Composable
private fun Title() {
    Text(text = "Start", style = MaterialTheme.typography.h1)
}