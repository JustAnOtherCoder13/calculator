package com.piconemarc.calculator.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piconemarc.calculator.ui.theme.*
import com.piconemarc.calculator.utils.GameLevel

@Composable
fun <T> BaseToggleButton(
    buttonText: T,
    onChecked: (buttonText: T, isChecked: Boolean) -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }

    if (buttonText is String || buttonText is Int) {
        Box(
            modifier = Modifier
                .height(TableButtonHeight)
                .width(TableButtonWidth)
                .background(
                    color = if (!isChecked) MaterialTheme.colors.secondary
                    else MaterialTheme.colors.secondaryVariant,
                    shape = CircleShape
                )
                .padding(LittleMarge)
                .clickable {
                    isChecked = !isChecked
                    onChecked(buttonText, isChecked)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = buttonText.toString())
        }
    } else {
        Log.e(
            "StartNewGame",
            "BaseTableToggleButton: only String or Int could be passed in param"
        )
    }
}

@Composable
fun GameLevelRadioButton(
    gameLevel: GameLevel,
    onChecked: (gameLevel: GameLevel) -> Unit,
    isChecked: Boolean
) {
    Box(
        modifier = Modifier
            .height(TableButtonHeight)
            .background(
                color = if (!isChecked) MaterialTheme.colors.secondary
                else MaterialTheme.colors.secondaryVariant,
                shape = CircleShape
            )
            .padding(LittleMarge)
            .clickable { onChecked(gameLevel) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = gameLevel.value)
    }
}

@Composable
fun GreenOutlinedColumn(
    padding: PaddingValues = PaddingValues(vertical = RegularMarge, horizontal = LittleMarge),
    body: @Composable () -> Unit
) = Column(
    modifier = Modifier
        .padding(padding)
        .fillMaxWidth()
        .border(ThinBorder, MaterialTheme.colors.secondaryVariant, RoundedCornerShape(10.dp)),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    body()
}