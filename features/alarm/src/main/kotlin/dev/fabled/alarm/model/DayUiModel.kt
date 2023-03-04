package dev.fabled.alarm.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
data class DayUiModel(
    val dayName: String,
    val dayOfWeekNumber: Int,
    val isChecked: MutableState<Boolean> = mutableStateOf(value = false)
) {
    companion object {
        fun provideWeekDays() = listOf(
            DayUiModel(dayName = "Mon", dayOfWeekNumber = 1),
            DayUiModel(dayName = "Tue", dayOfWeekNumber = 2),
            DayUiModel(dayName = "Wed", dayOfWeekNumber = 3),
            DayUiModel(dayName = "Thr", dayOfWeekNumber = 4),
            DayUiModel(dayName = "Fri", dayOfWeekNumber = 5),
            DayUiModel(dayName = "Sat", dayOfWeekNumber = 6),
            DayUiModel(dayName = "Sun", dayOfWeekNumber = 7),
        )
    }
}