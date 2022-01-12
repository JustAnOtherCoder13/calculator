package com.piconemarc.calculator.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.piconemarc.calculator.utils.interfaces.NavDestination

object NavDestinations {

    val Home = object : NavDestination {
        override val destination: String = "home"
    }

    val GameScreen = object : NavDestination {
        override val destination: String = "game"
        override val key: String = "gameParams"
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(key) { type = NavType.StringType }
        )
    }
}