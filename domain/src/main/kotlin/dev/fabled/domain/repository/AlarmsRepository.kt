package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun createNewAlarm(alarmModel: AlarmModel)

    suspend fun deleteAlarm(alarmId: Int)

    suspend fun checkUniqueAlarmName(alarmName: String): Boolean

    fun getAlarmsList(): Flow<List<AlarmModel>>

}