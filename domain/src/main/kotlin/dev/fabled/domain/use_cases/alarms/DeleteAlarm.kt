package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.domain.utils.AlarmUtil
import javax.inject.Inject

class DeleteAlarm @Inject constructor(
    private val alarmsRepository: AlarmsRepository,
    private val alarmUtil: AlarmUtil
) {

    suspend operator fun invoke(alarmModel: AlarmModel) {
        alarmsRepository.deleteAlarm(alarmId = alarmModel.alarmId)

        alarmUtil.removeAlarm(alarmModel)
    }

}