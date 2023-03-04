package dev.fabled.domain.use_cases.authorization

import dev.fabled.domain.model.ValidationResult
import javax.inject.Inject

class ValidateUsername @Inject constructor() {

    operator fun invoke(username: String): ValidationResult {
        if (username.isBlank()) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Username can not be empty"
        )

        if (username.length < 4) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Username should contain at least 4 characters"
        )

        if (username.length > 30) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Username can contain max 30 characters"
        )

        return ValidationResult(isSuccessFull = true)
    }

}