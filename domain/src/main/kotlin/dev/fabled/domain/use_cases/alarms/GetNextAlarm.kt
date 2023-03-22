package dev.fabled.domain.use_cases.alarms

import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.model.Resource
import dev.fabled.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class GetNextAlarm @Inject constructor(
    private val alarmsRepository: AlarmsRepository
) {

    suspend operator fun invoke(): Resource<AlarmModel> {
        val alarmList = alarmsRepository
            .getAlarmsList()
            .first()

        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_WEEK, -1)
        }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val correctDaysAlarms = arrayListOf<AlarmModel>()

        for (alarm in alarmList)
            if (dayOfWeek in alarm.alarmDays)
                correctDaysAlarms += alarm

        if (correctDaysAlarms.isEmpty())
            return Resource.Idle

        val currentTime = calendar.timeInMillis
        var alarmTime: Long

        var minTimeDistance = 1.days.inWholeMilliseconds
        var targetAlarm: AlarmModel? = null

        for (alarm in correctDaysAlarms) {
            alarmTime = calendar.apply {
                set(Calendar.HOUR_OF_DAY, alarm.alarmHour)
                set(Calendar.MINUTE, alarm.alarmMinute)
            }.timeInMillis

            if (alarmTime < currentTime) continue

            if (alarmTime - currentTime < minTimeDistance) {
                targetAlarm = alarm
                minTimeDistance = alarmTime - currentTime
            }
        }

        return targetAlarm?.let { Resource.Success(data = it) } ?: Resource.Idle
    }

}