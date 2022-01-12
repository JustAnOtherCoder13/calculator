package com.piconemarc.calculator.utils.interfaces

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import com.piconemarc.calculator.navigation.NavDestinations

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

interface NavDestination {

    val destination: String
    val key: String get() = ""
    val arguments: List<NamedNavArgument> get() = emptyList()

    fun doNavigation(navController: NavController) {
        navController.navigate(destination) {
            popUpTo(NavDestinations.Home.destination) {}
            launchSingleTop = true
        }
    }

    fun doNavigation(navController: NavController, argument : String = "") {
        navController.navigate("$destination/${argument}") {
            popUpTo(NavDestinations.Home.destination) {}
            launchSingleTop = true
        }
    }

    fun getRoute(): String = if (key.trim().isEmpty()) destination
    else "${destination}/{$key}"

}
