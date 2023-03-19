package dev.fabled.navigation.navigation_directions

import dev.fabled.navigation.navigation_core.NavigationDestination

object AlarmDirections {

    const val ALARM_DIRECTION = "alarm_direction"

    object AlarmsScreen : NavigationDestination {

        override fun route(): String = ALARMS_BOTTOM_DESTINATION_ROUTE

        private const val ALARMS_BOTTOM_DESTINATION_ROUTE = "alarms_screen"

    }

    object AlarmEditScreen : NavigationDestination {

        override fun route(): String = ALARM_EDIT_DESTINATION_ROUTE

        private const val ALARM_EDIT_DESTINATION_ROUTE = "alarm_edit_screen"

    }

    object AlarmSoundScreen : NavigationDestination {

        override fun route(): String = ALARM_SOUND_DESTINATION_NAV_ROUTE

        private const val ALARM_SOUND_DESTINATION_NAV_ROUTE = "alarm_sound"

    }

}