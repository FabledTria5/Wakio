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

        val alarmCreationResult = if (!applicationUtil.isInternetAvailable())
            createAndSyncNewAlarm(alarmModel)
        else
            createAlarmOffline(alarmModel)

        emit(alarmCreationResult)
    }

    private suspend fun createAlarmOffline(
        alarmModel: AlarmModel,
        depth: Int = 0
    ): Resource<Nothing> {
        val isAlarmExists =
            alarmsRepository.checkUniqueAlarmName(alarmName = alarmModel.alarmName)

        if (!isAlarmExists) {
            alarmsRepository.createNewAlarmOffline(alarmModel)
            return Resource.Completed
        } else {
            val nextDepth = depth + 1
            createAlarmOffline(
                alarmModel = alarmModel.copy(alarmName = "${alarmModel.alarmName}${nextDepth}"),
                depth = nextDepth
            )
        }

        return Resource.Error(error = DefaultErrors.UnknownError(""))
    }

    private suspend fun createAndSyncNewAlarm(alarmModel: AlarmModel): Resource<Nothing> {
        delay(1500)
        return Resource.Completed
    }
}