package com.piconemarc.calculator.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TopPlayerDTO(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String = "",
    val score : Long = 0L,
)
