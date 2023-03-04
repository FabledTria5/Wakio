package dev.fabled.on_boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.domain.repository.AppPreferencesRepository
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.AuthorizationDirections
import dev.fabled.on_boarding.model.OnBoardingPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val navigator: Navigator,
    private val appPreferencesRepository: AppPreferencesRepository
) : ViewModel() {

    private val _currentPage = MutableStateFlow<OnBoardingPage>(OnBoardingPage.FirstPage)
    val currentPage = _currentPage.asStateFlow()

    fun openNextPage() {
        when (_currentPage.value.page) {
            0 -> _currentPage.update { OnBoardingPage.SecondPage }
            1 -> _currentPage.update { OnBoardingPage.LastPage }
            2 -> openAuthorizationScreen()
        }
    }

    fun skipOnBoarding() = openAuthorizationScreen()

    private fun openAuthorizationScreen() = viewModelScope.launch {
        appPreferencesRepository.persistLaunchState()

        navigator.navigate(AuthorizationDirections.AuthorizationScreen.route())
    }

}