package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun createNewAlarm(alarmModel: AlarmModel): Long

    suspend fun deleteAlarm(alarmId: Int)

    suspend fun checkUniqueAlarmName(alarmName: String): String?

    fun getAlarmsList(): Flow<List<AlarmModel>>

}