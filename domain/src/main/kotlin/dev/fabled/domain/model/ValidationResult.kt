package dev.fabled.domain.model

data class ValidationResult(
    val isSuccessFull: Boolean,
    val errorMessage: String? = null
)
