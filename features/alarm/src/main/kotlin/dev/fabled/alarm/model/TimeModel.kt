package dev.fabled.alarm.model

import androidx.compose.runtime.Stable

@Stable
data class TimeModel(
    val hours: Int = 6,
    val minutes: Int = 30,
)
