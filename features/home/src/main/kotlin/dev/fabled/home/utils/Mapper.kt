package dev.fabled.home.utils

import dev.fabled.domain.model.AlarmModel
import dev.fabled.home.model.UiAlarm

fun AlarmModel.toUiModel(): UiAlarm = UiAlarm(
    alarmId = alarmId,
    alarmTime = "$alarmHour:" + if (alarmMinute < 10) "0$alarmMinute" else "$alarmMinute",
    isAlarmEnabled = isAlarmEnabled,
    alarmDays = alarmDays.toHashSet()
)