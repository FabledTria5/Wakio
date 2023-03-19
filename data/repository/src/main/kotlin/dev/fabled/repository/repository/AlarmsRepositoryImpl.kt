package dev.fabled.repository.repository

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.local.dao.AlarmsDao
import dev.fabled.repository.mapper.toAlarmEntity
import dev.fabled.repository.mapper.toAlarmsModelsList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val alarmsDao: AlarmsDao
) : AlarmsRepository {

    override suspend fun createNewAlarm(alarmModel: AlarmModel): Long {
        return -1
    }

    override suspend fun createNewAlarmOffline(alarmModel: AlarmModel) {
        val alarmEntity = alarmModel.toAlarmEntity()

        alarmsDao.insertAlarm(alarmEntity)
    }

    override suspend fun deleteAlarm(alarmId: Int) = alarmsDao.deleteAlarm(alarmId)

    override suspend fun checkUniqueAlarmName(alarmName: String): Boolean =
        alarmsDao.isAlarmExists(alarmName)

    override fun collectRemoteAlarms(): Flow<AlarmModel> {
        val query = firestore
            .collection("USERS_COLLECTION")
            .document("gDYILZbvaVS8SJtziAgZ5YoeXcy1")
            .collection("ALARMS_COLLECTION")

        return callbackFlow {
            val registration = query.addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Timber.e(error)
                    return@addSnapshotListener
                }

                for (document in snapshots!!.documentChanges) {
                    when (document.type) {
                        DocumentChange.Type.ADDED -> {

                        }

                        DocumentChange.Type.MODIFIED -> {

                        }

                        DocumentChange.Type.REMOVED -> {

                        }
                    }
                }
            }

            awaitClose {
                registration.remove()
            }
        }
    }

    override fun getAlarmsList(): Flow<List<AlarmModel>> = alarmsDao
        .getAlarms()
        .toAlarmsModelsList()

}