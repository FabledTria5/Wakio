package dev.fabled.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.fabled.local.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Query(value = "SELECT EXISTS(SELECT * FROM alarm_table WHERE alarm_name = :alarmName)")
    suspend fun isAlarmExists(alarmName: String): Boolean

    @Query(value = "SELECT * FROM alarm_table")
    fun getAlarms(): Flow<List<AlarmEntity>>

    @Query(value = "DELETE FROM alarm_table WHERE id = :alarmId")
    fun deleteAlarm(alarmId: Int)

}