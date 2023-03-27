package dev.fabled.repository.repository

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.local.dao.AlarmsDao
import dev.fabled.repository.mapper.toAlarmEntity
import dev.fabled.repository.mapper.toAlarmsModelsList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(
    private val alarmsDao: AlarmsDao
) : AlarmsRepository {

    override suspend fun createNewAlarm(alarmModel: AlarmModel): Long {
        val alarmEntity = alarmModel.toAlarmEntity()

        return alarmsDao.insertAlarm(alarmEntity)
    }

    override suspend fun deleteAlarm(alarmId: Int) = alarmsDao.deleteAlarm(alarmId)

    override suspend fun checkUniqueAlarmName(alarmName: String): String? =
        alarmsDao.isAlarmExists(alarmName)

    override fun getAlarmsList(): Flow<List<AlarmModel>> = alarmsDao
        .getAlarms()
        .toAlarmsModelsList()

}