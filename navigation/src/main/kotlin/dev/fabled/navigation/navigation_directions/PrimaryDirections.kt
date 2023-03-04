package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object PrimaryDirections {

    object ActivityScreen : NavigationDestination {

        override fun route(): String = ACTIVITY_BOTTOM_DESTINATION_ROUTE

        private const val ACTIVITY_BOTTOM_DESTINATION_ROUTE = "activity_screen"

    }

    object ProfileScreen : NavigationDestination {

        override fun route(): String = PROFILE_BOTTOM_DESTINATION_ROUTE

        private const val PROFILE_BOTTOM_DESTINATION_ROUTE = "profile_screen"

    }

}