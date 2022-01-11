package com.piconemarc.calculator.utils.interfaces

interface UiState
interface UiAction
interface BaseDTO

typealias Reducer <S> = (S, UiAction) -> S
typealias StoreSubscriber <S> = (S) -> Unit

interface Store<S : UiState> {
    fun dispatch(action: UiAction)
    fun add(subscriber: StoreSubscriber<S>): Boolean
    fun remove(subscriber: StoreSubscriber<S>): Boolean
}

interface DTO<M : BaseUiModel, D : BaseDTO> : BaseDTO {
    val id: Long
    val name: String
    fun fromUiModel(model: M): D
    fun toUiModel(): M
}

