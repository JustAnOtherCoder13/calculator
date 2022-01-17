package com.piconemarc.calculator.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.piconemarc.calculator.model.ui.PlayedGameInfo
import com.piconemarc.calculator.ui.common.GreenOutlinedColumn

@Composable
fun GameOverScreen(playedGameInfo: PlayedGameInfo){
    GreenOutlinedColumn() {
        Text(text = "Game Over", style = MaterialTheme.typography.h1)
        Row() {
            Text(text = "answered questions : ", style = MaterialTheme.typography.h3)
            Text(text = playedGameInfo.answeredQuestions.toString(), style = MaterialTheme.typography.body1)
        }
        Row() {
            Text(text = "score : ", style = MaterialTheme.typography.h3)
            Text(text = playedGameInfo.score.toString(), style = MaterialTheme.typography.body1)
        }
    }
}