package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAlarmsRepository : AlarmsRepository {

    private var lastCreatedAlarm = 0

    private val database = ArrayDeque<AlarmModel>()

    override suspend fun createNewAlarm(alarmModel: AlarmModel) {
        database.addLast(alarmModel.copy(alarmId = lastCreatedAlarm + 1))
        ++lastCreatedAlarm
    }

    override suspend fun deleteAlarm(alarmId: Int) {
        database.removeLastOrNull()
    }

    override suspend fun checkUniqueAlarmName(alarmName: String): Boolean {
        return database.any { it.alarmName == alarmName }
    }

    override fun getAlarmsList(): Flow<List<AlarmModel>> {
        return flowOf(database.toList())
    }
}