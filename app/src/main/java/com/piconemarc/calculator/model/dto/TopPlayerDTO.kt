package com.piconemarc.calculator.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piconemarc.calculator.model.ui.TopPlayer
import com.piconemarc.calculator.utils.interfaces.DTO

@Entity
data class TopPlayerDTO(
    @PrimaryKey(autoGenerate = true)
    override val id : Long = 0,
    override val name : String = "",
    val score : Long = 0L,
): DTO<TopPlayer,TopPlayerDTO> {
    override fun fromUiModel(model: TopPlayer): TopPlayerDTO {
        return this.copy(
            id = model.id,
            name = model.name,
            score = model.score
        )
    }

    override fun toUiModel(): TopPlayer {
        return TopPlayer(id, name, score)
    }
}
