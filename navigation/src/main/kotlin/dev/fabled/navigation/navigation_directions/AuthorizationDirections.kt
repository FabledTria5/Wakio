package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object AuthorizationDirections {

    object AuthorizationScreen : NavigationDestination {

        override val isInclusive: Boolean = true

        override fun route(): String = AUTHORIZATION_DESTINATION_ROUTE

        private const val AUTHORIZATION_DESTINATION_ROUTE = "authorization_screen"

    }

    object SetupScreen : NavigationDestination {

        override val isInclusive: Boolean = true

        override fun route(): String = SETUP_DESTINATION_ROUTE

        private const val SETUP_DESTINATION_ROUTE = "setup_screen"

    }

}