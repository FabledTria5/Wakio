package dev.fabled.navigation.navigation_directions

import androidx.annotation.DrawableRes
import dev.fabled.navigation.R
import dev.fabled.navigation.navigation_core.NavigationDestination

sealed class BottomNavigationDestinations(
    @DrawableRes val selectedIcon: Int,
    val unselectedIcon: Int,
    val direction: NavigationDestination
) {

    object Home : BottomNavigationDestinations(
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected,
        direction = HomeDirections.HomeScreen
    )

    object Activity : BottomNavigationDestinations(
        selectedIcon = R.drawable.ic_activity_selected,
        unselectedIcon = R.drawable.ic_activity_unselected,
        direction = PrimaryDirections.ActivityScreen
    )

    object Alarm : BottomNavigationDestinations(
        selectedIcon = R.drawable.ic_alarm_selected,
        unselectedIcon = R.drawable.ic_alarm_unselected,
        direction = AlarmDirections.AlarmsScreen
    )

    object Profile : BottomNavigationDestinations(
        selectedIcon = R.drawable.ic_profile_selected,
        unselectedIcon = R.drawable.ic_profile_unselected,
        direction = PrimaryDirections.ProfileScreen
    )

    companion object {
        fun getDestinations() = listOf(Home, Activity, Alarm, Profile)
    }

}