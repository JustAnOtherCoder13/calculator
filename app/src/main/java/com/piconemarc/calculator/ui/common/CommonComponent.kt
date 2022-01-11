package com.piconemarc.calculator.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> BaseTableToggleButton(
    buttonText: T,
    onChecked: (buttonText: T, isChecked: Boolean) -> Unit,
    width: Dp = 30.dp
) {
    var isChecked by remember { mutableStateOf(false)}

    if (buttonText is String || buttonText is Int) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .height(40.dp)
                .width(width)
                .background(
                    color = if (!isChecked) MaterialTheme.colors.secondary
                    else MaterialTheme.colors.secondaryVariant,
                    shape = CircleShape
                )
                .padding(5.dp)
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