package dev.fabled.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "alarm_name")
    val alarmName: String,
    @ColumnInfo(name = "alarm_hour")
    val alarmHour: Int,
    @ColumnInfo(name = "alarm_minute")
    val alarmMinute: Int,
    @ColumnInfo(name = "alarm_days")
    val daysOfWeek: String,
    @ColumnInfo(name = "alarm_sound_tag")
    val alarmSoundTag: String,
    @ColumnInfo(name = "alarm_volume")
    val alarmVolume: Float,
    @ColumnInfo(name = "vibration_enabled")
    val isVibrationEnabled: Boolean,
    @ColumnInfo(name = "is_alarm_enabled")
    val isAlarmEnabled: Boolean,
    @ColumnInfo(name = "alarm_gradient_tag")
    val alarmGradientTag: String
)