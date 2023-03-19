package dev.fabled.authorization

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fabled.authorization.model.AuthorizationEvent
import dev.fabled.authorization.model.AuthorizationState
import dev.fabled.authorization.model.ProfileBackgroundModel
import dev.fabled.authorization.model.SetupModel
import dev.fabled.common.code.model.Gender
import dev.fabled.domain.model.Resource
import dev.fabled.domain.model.UserModel
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.use_cases.authorization.ValidationCases
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_directions.AuthorizationDirections
import dev.fabled.navigation.navigation_directions.HomeDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Stable
class AuthorizationViewModel @Inject constructor(
    private val navigator: Navigator,
    private val validationCases: ValidationCases,
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val _authorizationState = MutableStateFlow<Resource<Nothing>>(Resource.Idle)
    val authorizationState = _authorizationState.asStateFlow()

    private val _signUpAuthorizationState = MutableStateFlow(AuthorizationState.emptyState())
    val signUpAuthorizationState = _signUpAuthorizationState.asStateFlow()

    private val _signInAuthorizationState = MutableStateFlow(AuthorizationState.emptyState())
    val signInAuthorizationState = _signInAuthorizationState.asStateFlow()

    private val _setupState = MutableStateFlow(SetupModel())
    val setupState = _setupState.asStateFlow()

    private fun openSetupScreen() {
        navigator.navigate(
            route = AuthorizationDirections.SetupScreen.route(),
            builder = {
                popUpTo(route = AuthorizationDirections.AuthorizationScreen.route()) {
                    inclusive = true
                }
            }
        )
    }

    private fun openHomeScreen() {
        navigator.navigate(
            route = HomeDirections.HomeScreen.route(),
            builder = {
                popUpTo(AuthorizationDirections.AUTHORIZATION_DIRECTION) {
                    inclusive = true
                }
            }
        )
    }

    fun onSignInEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.EmailChanged -> _signInAuthorizationState.update {
                it.copy(email = event.email)
            }

            is AuthorizationEvent.PasswordChanged -> _signInAuthorizationState.update {
                it.copy(password = event.password)
            }

            else -> Unit
        }
    }

    fun onSignUpEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.EmailChanged -> _signUpAuthorizationState.update {
                it.copy(email = event.email)
            }

            is AuthorizationEvent.UsernameChanged -> _signUpAuthorizationState.update {
                it.copy(username = event.username)
            }

            is AuthorizationEvent.PasswordChanged -> _signUpAuthorizationState.update {
                it.copy(password = event.password)
            }
        }
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = _signInAuthorizationState.value.email
            val password = _signInAuthorizationState.value.password

            val emailValidationResult = validationCases.validateEmail(email)
            val passwordValidationResult = validationCases.validatePassword(password)

            if (emailValidationResult.isSuccessFull && passwordValidationResult.isSuccessFull) {
                authorizationRepository
                    .signInFirebase(email = email, password = password)
                    .collect { resource ->
                        if (resource is Resource.Completed) openHomeScreen()
                        _authorizationState.update { resource }
                    }
                return@launch
            }

            _signInAuthorizationState.update { state ->
                state.copy(
                    emailError = emailValidationResult.errorMessage,
                    passwordError = passwordValidationResult.errorMessage
                )
            }
        }
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            val email = _signUpAuthorizationState.value.email.trim()
            val username = _signUpAuthorizationState.value.username.trim()
            val password = _signUpAuthorizationState.value.password.trim()

            val emailValidationResult = validationCases.validateEmail(email)
            val usernameValidationResult = validationCases.validateUsername(username)
            val passwordValidationResult = validationCases.validatePassword(password)

            val validationResults = listOf(
                emailValidationResult,
                usernameValidationResult,
                passwordValidationResult
            )

            if (validationResults.all { it.isSuccessFull }) {
                openSetupScreen()
                return@launch
            }

            _signUpAuthorizationState.update { state ->
                state.copy(
                    emailError = emailValidationResult.errorMessage,
                    passwordError = passwordValidationResult.errorMessage
                )
            }
        }
    }

    fun updateGender(genderModel: Gender?) {
        _setupState.update { it.copy(genderModel = genderModel) }
    }

    fun updateProfileBackground(profileBackgroundModel: ProfileBackgroundModel) {
        _setupState.update { it.copy(profileBackgroundModel = profileBackgroundModel) }
    }

    fun finishSignUp() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = _signUpAuthorizationState.value.email.trim()
            val username = _signUpAuthorizationState.value.username.trim()
            val password = _signUpAuthorizationState.value.password.trim()

            val gender = _setupState.value.genderModel
            val backgroundPic = _setupState.value.profileBackgroundModel

            checkNotNull(gender) { return@launch }

            val userModel = UserModel(
                username = username,
                userGender = gender.tag,
                backgroundPicTag = backgroundPic.tag
            )

            authorizationRepository
                .signUpFirebase(email = email, password = password, userModel = userModel)
                .collect { resource ->
                    if (resource is Resource.Completed) openHomeScreen()
                    _authorizationState.update { resource }
                }
        }
    }

}