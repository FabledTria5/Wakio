package dev.fabled.domain.utils

import dev.fabled.domain.model.AlarmModel

interface AlarmUtil {

    fun setAlarm(alarmModel: AlarmModel)

    fun removeAlarm(alarmModel: AlarmModel)

}