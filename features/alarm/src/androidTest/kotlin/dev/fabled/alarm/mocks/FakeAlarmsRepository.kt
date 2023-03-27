package dev.fabled.alarm.mocks

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAlarmsRepository : AlarmsRepository {

    override suspend fun createNewAlarm(alarmModel: AlarmModel): Long {
        return -1L
    }

    override suspend fun deleteAlarm(alarmId: Int) {

    }

    override suspend fun checkUniqueAlarmName(alarmName: String): String? {
        return null
    }

    override fun getAlarmsList(): Flow<List<AlarmModel>> {
        return flowOf(emptyList())
    }
}