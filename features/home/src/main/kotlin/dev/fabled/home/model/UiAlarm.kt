package dev.fabled.home.model

import androidx.compose.runtime.Stable

@Stable
data class UiAlarm(
    val alarmId: Int,
    val alarmTime: String,
    val isAlarmEnabled: Boolean,
    val alarmDays: HashSet<Int>
)
