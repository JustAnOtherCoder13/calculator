package com.piconemarc.calculator.model.ui

data class PlayedGameInfo(
    val score : Long,
    val answeredQuestions : Int,
    val goodAnswer : Int,
    val bestGoodAnswerChain : Int,
    val answerPerTime : String,
)
