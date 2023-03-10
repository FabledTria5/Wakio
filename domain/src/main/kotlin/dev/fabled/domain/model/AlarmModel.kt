package dev.fabled.domain.model

data class AlarmModel(
    val alarmId: Int,
    val alarmName: String,
    val alarmHour: Int,
    val alarmMinute: Int,
    val alarmDays: List<Int>,
    val alarmSoundTag: String,
    val alarmVolume: Float,
    val isVibrationEnabled: Boolean,
    val isAlarmEnabled: Boolean,
    val gradientTag: String,
    val createdAt: Long
)