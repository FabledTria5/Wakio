package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.domain.utils.AlarmUtil
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewAlarm @Inject constructor(
    private val alarmUtil: AlarmUtil,
    private val alarmsRepository: AlarmsRepository
) {

    suspend operator fun invoke(alarmModel: AlarmModel) = flow {
        emit(Resource.Loading)

        val alarmUniqueName = createUniqueAlarmName(alarmModel.alarmName)
        val newAlarmId = alarmsRepository.createNewAlarm(
            alarmModel = alarmModel.copy(alarmName = alarmUniqueName)
        )

        alarmUtil.setAlarm(alarmModel = alarmModel, uniqueId = newAlarmId.hashCode())

        emit(Resource.Completed)
    }

    private suspend fun createUniqueAlarmName(name: String): String {
        val existingAlarm = alarmsRepository.checkUniqueAlarmName(alarmName = name) ?: return name

        val uniqueAlarmName = if (existingAlarm.last() != ')') "$name(1)"
        else {
            var i = existingAlarm.lastIndex - 1
            var num = 0
            var d = 1

            while (existingAlarm[i] != '(') {
                num += (existingAlarm[i--] - '0') * d
                d *= 10
            }

            name.dropLastWhile { it != '(' } + "${++num})"
        }

        return createUniqueAlarmName(uniqueAlarmName)
    }
}