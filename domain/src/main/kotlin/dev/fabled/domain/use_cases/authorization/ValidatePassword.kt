package dev.fabled.domain.use_cases.authorization

import dev.fabled.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePassword @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Password can not be empty"
        )

        if (password.length < 8) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Password should contain at least 8 characters"
        )

        if (!password.any { it.isUpperCase() }) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Password should contain at least 1 uppercase letter"
        )

        if (password.all { it.isLetter() })
            return ValidationResult(
                isSuccessFull = false,
                errorMessage = "Password should contain at least 1 digit"
            )

        return ValidationResult(isSuccessFull = true)
    }

}