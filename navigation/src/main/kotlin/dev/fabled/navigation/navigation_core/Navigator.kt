package dev.fabled.navigation.navigation_core

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface Navigator {

    fun navigateUp(): Boolean

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }
    ): Boolean

    val destinations: Flow<NavigatorEvent>

}