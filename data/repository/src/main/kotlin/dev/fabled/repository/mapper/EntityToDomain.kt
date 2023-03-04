package dev.fabled.repository.mapper

import dev.fabled.domain.model.AlarmModel
import dev.fabled.local.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<AlarmEntity>>.toAlarmsModelsList(): Flow<List<AlarmModel>> = map { list ->
    list.map { entity ->
        AlarmModel(
            alarmId = entity.id,
            alarmName = entity.alarmName,
            alarmHour = entity.alarmHour,
            alarmMinute = entity.alarmMinute,
            alarmDays = entity.daysOfWeek
                .split(";")
                .map { it.toInt() },
            alarmSoundTag = entity.alarmSoundTag,
            alarmVolume = entity.alarmVolume,
            isVibrationEnabled = entity.isVibrationEnabled,
            isAlarmEnabled = entity.isAlarmEnabled,
            gradientTag = entity.alarmGradientTag
        )
    }
}