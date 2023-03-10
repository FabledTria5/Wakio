package dev.fabled.alarm.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.LocalDate

@Stable
data class AlarmUiModel(
    val alarmId: Int = -1,
    val creationTime: Long = System.currentTimeMillis(),
    val alarmName: MutableState<String> = mutableStateOf(value = "New Alarm"),
    val alarmTime: MutableState<TimeModel> = mutableStateOf(TimeModel()),
    val alarmDays: SnapshotStateList<DayUiModel> = mutableStateListOf<DayUiModel>().apply {
        val nextDayValue = LocalDate
            .now()
            .plusDays(1)
            .dayOfWeek
            .value

        val daysList = DayUiModel
            .provideWeekDays()
            .map { model ->
                if (model.dayOfWeekNumber == nextDayValue)
                    model.copy(isChecked = mutableStateOf(true))
                else model
            }

        addAll(daysList)
    },
    val alarmDelay: MutableState<String> = mutableStateOf(value = ""),
    val alarmSoundModel: MutableState<AlarmSoundModel> = mutableStateOf(AlarmSoundModel.BakeKujira),
    val alarmVolume: MutableState<Float> = mutableStateOf(value = .3f),
    val isVibrating: MutableState<Boolean> = mutableStateOf(value = true),
    val isAlarmEnabled: MutableState<Boolean> = mutableStateOf(value = true),
    val gradientUiModel: MutableState<GradientUiModel> = mutableStateOf(GradientUiModel.getRandom())
)
