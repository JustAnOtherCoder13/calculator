package com.piconemarc.calculator.model.ui

import com.piconemarc.calculator.utils.GameLevel

data class GameParameters(
    val tableList : List<Int>,
    val operandList : List<String>,
    val gameLevel : GameLevel
)

