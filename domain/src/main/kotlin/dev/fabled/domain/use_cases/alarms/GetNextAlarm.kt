package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class GetNextAlarm @Inject constructor(
    private val alarmsRepository: AlarmsRepository
) {

    operator fun invoke() = alarmsRepository.getAlarmsList()
        .map { alarmList ->
            val localDateTime = LocalDateTime.now()

            val nextAlarm = alarmList
                .filter { alarmModel ->
                    localDateTime.dayOfWeek.value in alarmModel.alarmDays
                }
                .ifEmpty { return@map Resource.Idle }
                .filter { alarmModel ->
                    localDateTime
                        .withHour(alarmModel.alarmHour)
                        .withMinute(alarmModel.alarmMinute) >= localDateTime
                }
                .ifEmpty { return@map Resource.Idle }
                .minBy { alarmModel ->
                    localDateTime
                        .withHour(alarmModel.alarmHour)
                        .withMinute(alarmModel.alarmMinute)
                }

            Resource.Success(data = nextAlarm)
        }

}