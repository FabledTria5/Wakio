package dev.fabled.navigation.navigation_core

import androidx.navigation.NamedNavArgument

interface NavigationDestination {

    fun route(): String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val isInclusive: Boolean
        get() = false

}