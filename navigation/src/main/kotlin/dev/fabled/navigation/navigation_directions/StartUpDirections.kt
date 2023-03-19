package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object StartUpDirections {

    const val START_UP_DIRECTION = "start_up_direction"

    object OnBoarding : NavigationDestination {

        override val isInclusive: Boolean = true

        override fun route(): String = ON_BOARDING_DESTINATION_ROUTE

        private const val ON_BOARDING_DESTINATION_ROUTE = "on_boarding_screen"

    }

}