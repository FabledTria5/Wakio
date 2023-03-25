package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

class FakeAlarmsRepository : AlarmsRepository {

    private var lastCreatedAlarm = 0

    private val database = arrayListOf<AlarmModel>()

    val shared = MutableStateFlow<List<AlarmModel>>(emptyList())

    override suspend fun createNewAlarm(alarmModel: AlarmModel) {
        database.add(alarmModel.copy(alarmId = lastCreatedAlarm++))
        ++lastCreatedAlarm

        shared.emit(database)
    }

    override suspend fun deleteAlarm(alarmId: Int) {
        database.clear()
        shared.emit(database)
    }

    override suspend fun checkUniqueAlarmName(alarmName: String): Boolean {
        return database.any { it.alarmName == alarmName }
    }

    override fun getAlarmsList(): SharedFlow<List<AlarmModel>> {
        return shared
    }
}