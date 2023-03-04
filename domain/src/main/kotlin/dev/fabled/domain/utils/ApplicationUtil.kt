package dev.fabled.domain.utils

import dev.fabled.domain.model.AlarmModel

interface ApplicationUtil {

    fun isInternetAvailable(): Boolean

    fun setupAlarm(alarmModel: AlarmModel, alarmId: Long)

}