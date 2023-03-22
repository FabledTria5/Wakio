package dev.fabled.wakio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.domain.repository.AppPreferencesRepository
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.AuthorizationDirections
import dev.fabled.navigation.navigation_directions.HomeDirections
import dev.fabled.navigation.navigation_directions.StartUpDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val authorizationRepository: AuthorizationRepository,
    navigator: Navigator
) : ViewModel(), Navigator by navigator {

    private val _isLoading = MutableStateFlow(value = true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            if (BuildConfig.DEBUG) {
                navigate(
                    route = HomeDirections.HOME_DIRECTION,
                    builder = { popUpTo("stab") { inclusive = true } }
                )
                _isLoading.update { false }
                return@launch
            }

            val isFirstLaunch = appPreferencesRepository.isFirstLaunch()
            val isUserAuthenticated = authorizationRepository
                .isUserAuthenticated()
                .first()

            if (isFirstLaunch) {
                navigate(
                    route = StartUpDirections.START_UP_DIRECTION,
                    builder = { popUpTo("stab") { inclusive = true } }
                )
                _isLoading.update { false }
                return@launch
            }

            if (isUserAuthenticated) {
                navigate(
                    route = HomeDirections.HOME_DIRECTION,
                    builder = { popUpTo("stab") { inclusive = true } }
                )
                _isLoading.update { false }
                return@launch
            }

            navigate(
                route = AuthorizationDirections.AUTHORIZATION_DIRECTION,
                builder = { popUpTo("stab") { inclusive = true } }
            )
            _isLoading.update { false }
        }
    }

}