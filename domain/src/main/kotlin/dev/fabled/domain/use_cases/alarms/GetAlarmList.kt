package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.repository.AlarmsRepository
import javax.inject.Inject

class GetAlarmList @Inject constructor(
    private val alarmsRepository: AlarmsRepository
) {

    operator fun invoke() = alarmsRepository.getAlarmsList()

}