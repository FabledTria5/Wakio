package dev.fabled.authorization.model

import androidx.compose.runtime.Stable
import dev.fabled.common.code.model.Gender

@Stable
data class SetupModel(
    val genderModel: Gender? = null,
    val profileBackgroundModel: ProfileBackgroundModel = ProfileBackgroundModel.BeforeSunrise
)
