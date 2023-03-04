package dev.fabled.authorization.model

import androidx.compose.runtime.Stable

@Stable
data class AuthorizationState(
    val email: String,
    val emailError: String? = null,
    val username: String,
    val usernameError: String? = null,
    val password: String,
    val passwordError: String? = null
) {
    companion object {
        fun emptyState() = AuthorizationState(email = "", username = "", password = "")
    }
}
