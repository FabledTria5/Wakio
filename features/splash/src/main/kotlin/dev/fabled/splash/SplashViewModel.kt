package dev.fabled.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptionsBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.domain.repository.AppPreferencesRepository
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.AuthorizationDirections
import dev.fabled.navigation.navigation_directions.HomeDirections
import dev.fabled.navigation.navigation_directions.StartUpDirections
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator,
    private val appPreferencesRepository: AppPreferencesRepository,
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val splashScreenNavOptionsBuilder: NavOptionsBuilder.() -> Unit = {
        popUpTo(route = StartUpDirections.SplashScreen.route()) {
            inclusive = true
        }
    }

    fun navigateForward() = viewModelScope.launch {
        val isFirstLaunch = appPreferencesRepository.isFirstLaunch()
        val isUserAuthorized = authorizationRepository
            .isUserAuthenticated()
            .first()

        if (isFirstLaunch) {
            navigator.navigate(
                route = StartUpDirections.OnBoarding.route(),
                builder = splashScreenNavOptionsBuilder
            )
        } else {
            if (isUserAuthorized) navigator.navigate(
                route = HomeDirections.HomeScreen.route(),
                builder = splashScreenNavOptionsBuilder
            )
            else navigator.navigate(
                route = AuthorizationDirections.AuthorizationScreen.route(),
                builder = splashScreenNavOptionsBuilder
            )
        }
    }
}