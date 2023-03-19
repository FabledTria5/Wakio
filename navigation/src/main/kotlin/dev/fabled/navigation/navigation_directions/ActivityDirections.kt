package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object ActivityDirections {

    const val ACTIVITY_DIRECTION = "activity_direction"

    object ActivityScreen : NavigationDestination {
        override fun route(): String = ACTIVITY_BOTTOM_DESTINATION_ROUTE

        private const val ACTIVITY_BOTTOM_DESTINATION_ROUTE = "activity_screen"

    }

}