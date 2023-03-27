package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeAlarmsRepository : AlarmsRepository {

    private var lastCreatedAlarm = 0

    private val database = arrayListOf<AlarmModel>()
    private val shared = MutableStateFlow<List<AlarmModel>>(database)

    override suspend fun createNewAlarm(alarmModel: AlarmModel): Long {
        database.add(alarmModel.copy(alarmId = lastCreatedAlarm++))

        shared.update { database }

        return lastCreatedAlarm.toLong()
    }

    override suspend fun deleteAlarm(alarmId: Int) {
        database.clear()
        shared.emit(database)
    }

    override suspend fun checkUniqueAlarmName(alarmName: String): String? {
        return database.find { it.alarmName == alarmName }?.alarmName
    }

    override fun getAlarmsList(): Flow<List<AlarmModel>> {
        return shared
    }
}