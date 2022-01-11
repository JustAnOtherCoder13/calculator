package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.piconemarc.calculator.ui.common.BaseTableToggleButton

@Composable
fun TableList(
    onTableListChange: (tableNumber: Int, isChecked: Boolean) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Choose your table")
        Row {
            for (i in 1..10) {
                BaseTableToggleButton(
                    buttonText = i,
                    onChecked = onTableListChange
                )
            }
        }
    }

}
