package com.piconemarc.calculator.model.ui

import com.piconemarc.calculator.utils.interfaces.BaseUiModel

data class TopPlayer(
    override val id: Long = 0,
    override val name : String = "",
    val score : Long = 0L,
): BaseUiModel()