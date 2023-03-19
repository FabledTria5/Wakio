package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object ProfileDirections {

    const val PROFILE_DIRECTION = "profile_direction"

    object ProfileScreen : NavigationDestination {

        override fun route(): String = PROFILE_BOTTOM_DESTINATION_ROUTE

        private const val PROFILE_BOTTOM_DESTINATION_ROUTE = "profile_screen"

    }

}