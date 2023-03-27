package dev.fabled.alarm.mocks

import androidx.navigation.NavOptionsBuilder
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_core.NavigatorEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNavigator : Navigator {

    override fun navigateUp(): Boolean {
        return true
    }

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit): Boolean {
        return true
    }

    override val destinations: Flow<NavigatorEvent>
        get() = flowOf(NavigatorEvent.NavigateUp)
}