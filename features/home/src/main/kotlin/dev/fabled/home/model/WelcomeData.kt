package dev.fabled.home.model

import androidx.compose.runtime.Stable
import dev.fabled.common.code.model.Gender

@Stable
data class WelcomeData(
    val username: String = "",
    val userGender: Gender? = null,
    val currentTime: String = ""
)