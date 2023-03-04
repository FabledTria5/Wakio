package dev.fabled.domain.use_cases.authorization

import dev.fabled.domain.model.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateEmail @Inject constructor() {

    companion object {
        private const val EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$"
    }

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Email can not be empty"
        )

        val pattern = Pattern.compile(EMAIL_PATTERN)
        if (!pattern.matcher(email).matches()) return ValidationResult(
            isSuccessFull = false,
            errorMessage = "Incorrect email format"
        )

        return ValidationResult(isSuccessFull = true)
    }

}