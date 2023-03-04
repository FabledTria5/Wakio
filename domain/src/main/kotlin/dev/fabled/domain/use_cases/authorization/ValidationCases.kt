package dev.fabled.domain.use_cases.authorization

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class ValidationCases @Inject constructor(
    val validateEmail: ValidateEmail,
    val validateUsername: ValidateUsername,
    val validatePassword: ValidatePassword
)
