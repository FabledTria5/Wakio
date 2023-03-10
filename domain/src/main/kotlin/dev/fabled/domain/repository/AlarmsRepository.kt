package dev.fabled.domain.repository

import dev.fabled.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun createNewAlarm(alarmModel: AlarmModel): Long

    suspend fun deleteAlarm(alarmId: Int)

    suspend fun createNewAlarmOffline(alarmModel: AlarmModel)

    suspend fun checkUniqueAlarmName(alarmName: String): Boolean

    fun collectRemoteAlarms(): Flow<AlarmModel>

    fun getAlarmsList(): Flow<List<AlarmModel>>

}