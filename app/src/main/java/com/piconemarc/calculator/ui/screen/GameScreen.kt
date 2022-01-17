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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.piconemarc.calculator.R
import com.piconemarc.calculator.reducer.GameAction
import com.piconemarc.calculator.reducer.GameState
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn
import com.piconemarc.calculator.ui.theme.*
import com.piconemarc.calculator.utils.GameLevel
import com.piconemarc.calculator.utils.GoodAnswerBonus


@Composable
fun GameScreen(
    gameState: GameState,
    onGameEvent: (gameAction: GameAction) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Title()
        Timer(remainingTime = gameState.remainingTime)
        Score(
            goodAnswerChain = gameState.goodAnswerChain.toString(),
            score = gameState.score.toString()
        )
        BonusInfo(bonus = gameState.bonus)
        Answer(
            firstNumber = gameState.firstNumber.toString(),
            operand = gameState.operand,
            secondNumber = gameState.secondNumber.toString(),
            resultValue = gameState.result,
            onResultChange = { result ->
                onGameEvent(GameAction.UpdateResult(result))
            },
            gameLevel = gameState.gameParameters.gameLevel,
            onValidateResult = { result ->
                onGameEvent(
                    GameAction.SubmitResult(
                        gameState = gameState,
                        result = result,
                        doOnSuccess = {
                            onGameEvent(
                                GameAction.UpdateScore(
                                    gameState = gameState,
                                    result = result
                                )
                            )
                        })
                )
            },
            resultList = gameState.noviceAnswerValues,
        )
    }
}

@Composable
private fun BonusInfo(bonus: Long) {
    Text(
        text = stringResource(R.string.GameBonusInfoTitle) + " $bonus",
        style = when (bonus) {
            GoodAnswerBonus.BASIC.value -> MaterialTheme.typography.h3
            GoodAnswerBonus.NOT_SO_BAD.value -> MaterialTheme.typography.h3
            GoodAnswerBonus.GOOD.value -> MaterialTheme.typography.h2
            GoodAnswerBonus.VERY_GOOD.value -> MaterialTheme.typography.h1
            else -> MaterialTheme.typography.body1
        }
    )
}

//-------------------------Base component--------------------------------------------
@Composable
private fun Title() {
    Text(text = stringResource(R.string.gameScreenTitle), style = MaterialTheme.typography.h1)
}

@Composable
private fun Timer(remainingTime: Long) {
    GreenOutlinedColumn {
        Text(text = stringResource(R.string.timerInfoTitle), style = MaterialTheme.typography.h2)
        Row {
            Text(text = "$remainingTime", style = MediumBigFontTextStyle)
        }
    }
}

@Composable
private fun Score(
    goodAnswerChain: String,
    score: String
) {
    GreenOutlinedColumn {
        Row {
            Text(
                text = stringResource(R.string.gameChainInfoTitle),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = goodAnswerChain,
                style = MaterialTheme.typography.h3
            )
        }
        Row {
            Text(text = stringResource(R.string.scoreGameInfoTitle), style = MediumBigFontTextStyle)
            Text(text = score, style = MediumBigFontTextStyle)
        }
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
    onValidateResult: (result: String) -> Unit,
    resultList: List<Int>
) {
    GreenOutlinedColumn(
        padding = PaddingValues(
            top = ScoreMarge,
            start = LittleMarge,
            end = LittleMarge
        )
    ) {
        Row {
            Text(text = firstNumber, style = BigFontTextStyle)
            Text(text = " $operand ", style = BigFontTextStyle)
            Text(text = secondNumber, style = BigFontTextStyle)
            Text(text = " =", style = BigFontTextStyle)
        }
        when (gameLevel) {
            GameLevel.NOVICE -> {
                ResultSelector(
                    onNoviceResultButtonClick = onValidateResult,
                    resultList
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


//----------------------Sub Component--------------------------------------------
@Composable
private fun ResultSelector(
    onNoviceResultButtonClick: (result: String) -> Unit,
    answerList: List<Int>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = RegularMarge),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        answerList.forEach { integerResult ->
            NoviceAnswerButton(
                value = integerResult.toString(),
                onButtonClick = { result -> onNoviceResultButtonClick(result) })
        }
    }
}

@Composable
private fun NoviceAnswerButton(
    value: String,
    onButtonClick: (value: String) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onButtonClick(value) }
            .size(NoviceAnswerButtonSize)
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
    onValidateResult: (result: String) -> Unit
) {
    TextField(
        textStyle = BigFontTextStyle,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onPrimary,
            focusedLabelColor = MaterialTheme.colors.onPrimary,
        ),
        modifier = Modifier
            .width(TextFieldWidth),
        value = resultValue,
        onValueChange = onResultChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        label = { Text(text = stringResource(R.string.resultTextFieldLabel)) }

    )
    BigButton(
        onButtonClick = { onValidateResult(resultValue) },
        buttonText = stringResource(R.string.validateAnswerButtonText)
    )
}
