package dev.fabled.alarm.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import dev.fabled.alarm.model.AlarmSoundModel
import dev.fabled.alarm.model.AlarmUiModel
import dev.fabled.alarm.model.DayUiModel
import dev.fabled.alarm.model.GradientUiModel
import dev.fabled.alarm.model.TimeModel
import dev.fabled.domain.model.AlarmModel

fun AlarmModel.toUiModel(): AlarmUiModel = AlarmUiModel(
    alarmId = alarmId,
    alarmName = mutableStateOf(alarmName),
    alarmTime = run {
        val timeModel = TimeModel(hours = alarmHour, minutes = alarmMinute)
        mutableStateOf(timeModel)
    },
    alarmDays = mutableStateListOf<DayUiModel>().apply {
        val daysList = DayUiModel.provideWeekDays()

        daysList.forEach { dayModel ->
            if (dayModel.dayOfWeekNumber in alarmDays)
                dayModel.isChecked.value = true
        }

        addAll(daysList)
    },
    alarmSoundModel = mutableStateOf(AlarmSoundModel.getByTag(alarmSoundTag)),
    alarmVolume = mutableStateOf(alarmVolume),
    isVibrating = mutableStateOf(isVibrationEnabled),
    isAlarmEnabled = mutableStateOf(isAlarmEnabled),
    gradientUiModel = mutableStateOf(GradientUiModel.getByTag(gradientTag))
)