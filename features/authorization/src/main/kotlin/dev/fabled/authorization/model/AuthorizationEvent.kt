package dev.fabled.authorization.model

import androidx.compose.runtime.Stable

@Stable
sealed class AuthorizationEvent {

    data class EmailChanged(val email: String): AuthorizationEvent()
    data class UsernameChanged(val username: String): AuthorizationEvent()
    data class PasswordChanged(val password: String): AuthorizationEvent()

}
