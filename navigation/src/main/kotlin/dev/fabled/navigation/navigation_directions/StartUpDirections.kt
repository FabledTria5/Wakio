package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object StartUpDirections {

    object SplashScreen : NavigationDestination {

        override val isInclusive: Boolean = true

        override fun route(): String = SPLASH_DESTINATION_ROUTE

        private const val SPLASH_DESTINATION_ROUTE = "splash_screen"

    }

    object OnBoarding : NavigationDestination {

        override val isInclusive: Boolean = true

        override fun route(): String = ON_BOARDING_DESTINATION_ROUTE

        private const val ON_BOARDING_DESTINATION_ROUTE = "on_boarding_screen"

    }

}