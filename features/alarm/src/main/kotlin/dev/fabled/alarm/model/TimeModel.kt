package dev.fabled.alarm.model

import androidx.compose.runtime.Stable
import java.time.LocalDateTime

@Stable
data class TimeModel(
    val hours: Int = LocalDateTime.now().hour,
    val minutes: Int = LocalDateTime.now().minute,
)
