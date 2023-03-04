package dev.fabled.repository.mapper

import dev.fabled.domain.model.AlarmModel
import dev.fabled.local.entities.AlarmEntity

fun AlarmModel.toAlarmEntity() = AlarmEntity(
    alarmName = alarmName,
    alarmHour = alarmHour,
    alarmMinute = alarmMinute,
    daysOfWeek = alarmDays.joinToString(separator = ";") { it.toString() },
    alarmSoundTag = alarmSoundTag,
    alarmVolume = alarmVolume,
    isVibrationEnabled = isVibrationEnabled,
    isAlarmEnabled = isAlarmEnabled,
    alarmGradientTag = gradientTag
)