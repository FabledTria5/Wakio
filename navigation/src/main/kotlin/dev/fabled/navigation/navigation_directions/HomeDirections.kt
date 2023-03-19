package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object HomeDirections {

    const val HOME_DIRECTION = "home_direction"

    object HomeScreen : NavigationDestination {

        override fun route(): String = HOME_BOTTOM_DESTINATION_ROUTE

        private const val HOME_BOTTOM_DESTINATION_ROUTE = "home_screen"

    }

    object NotificationsScreen : NavigationDestination {

        override fun route(): String = NOTIFICATIONS_SCREEN_DESTINATION_ROUTE

        private const val NOTIFICATIONS_SCREEN_DESTINATION_ROUTE = "notifications_screen"

    }

}