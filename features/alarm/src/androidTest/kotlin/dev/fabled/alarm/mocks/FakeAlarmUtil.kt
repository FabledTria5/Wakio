package dev.fabled.alarm.mocks

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.utils.AlarmUtil

class FakeAlarmUtil : AlarmUtil {

    override fun setAlarm(alarmModel: AlarmModel, uniqueId: Int) {

    }

    override fun removeAlarm(alarmModel: AlarmModel) {

    }
}