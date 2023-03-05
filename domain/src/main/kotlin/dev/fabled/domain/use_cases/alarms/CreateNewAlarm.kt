package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.DefaultErrors
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.domain.utils.ApplicationUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewAlarm @Inject constructor(
    private val applicationUtil: ApplicationUtil,
    private val alarmsRepository: AlarmsRepository
) {
    suspend operator fun invoke(alarmModel: AlarmModel) = flow {
        emit(Resource.Loading)

        val result = if (!applicationUtil.isInternetAvailable())
            createAndSyncNewAlarm(alarmModel)
        else
            createAlarmOffline(alarmModel)

        when (result) {
            is Resource.Success -> {
                applicationUtil.setupAlarm(alarmModel, result.data.toInt())
            }

            else -> emit(result)
        }

        emit(Resource.Completed)
    }

    private suspend fun createAlarmOffline(alarmModel: AlarmModel): Resource<Long> {
        val isAlarmExists =
            alarmsRepository.checkUniqueAlarmName(alarmName = alarmModel.alarmName)

        if (!isAlarmExists) {
            val newAlarmId = alarmsRepository.createNewAlarmOffline(alarmModel)

            return Resource.Success(data = newAlarmId)
        }

        return Resource.Error(error = DefaultErrors.UnknownError(""))
    }

    private suspend fun createAndSyncNewAlarm(alarmModel: AlarmModel): Resource<Long> {
        delay(1500)
        return Resource.Completed
    }
}