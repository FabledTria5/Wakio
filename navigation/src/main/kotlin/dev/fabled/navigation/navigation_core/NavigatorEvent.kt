package dev.fabled.navigation.navigation_core

import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent {
    object NavigateUp : NavigatorEvent()
    class Direction(val destination: String, val builder: NavOptionsBuilder.() -> Unit) :
        NavigatorEvent()
}
