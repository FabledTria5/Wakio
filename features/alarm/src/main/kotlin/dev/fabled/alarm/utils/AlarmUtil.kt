package dev.fabled.alarm.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.alarm.service.AlarmReceiver
import dev.fabled.alarm.service.AlarmService
import dev.fabled.domain.model.AlarmModel
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class AlarmUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val alarmManager by lazy { context.getSystemService(AlarmManager::class.java) }

    fun setupAlarm(alarmModel: AlarmModel) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmService.ALARM_SOUND_TAG, alarmModel.alarmSoundTag)
            putExtra(AlarmService.ALARM_VOLUME, alarmModel.alarmVolume)
            putExtra(AlarmService.IS_VIBRATION_ENABLED, alarmModel.isVibrationEnabled)
        }

        val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, alarmModel.alarmHour)
                set(Calendar.MINUTE, alarmModel.alarmMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        repeat(times = 7) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 in alarmModel.alarmDays) {
                val uniqueId = alarmModel.createdAt.toInt() + calendar.get(Calendar.DAY_OF_WEEK) - 1
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    uniqueId,
                    alarmIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    7.days.inWholeMilliseconds,
                    pendingIntent
                )

                Timber.d("Alarm with id $uniqueId has been scheduled for ${calendar.time}")
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
    }

    fun removeAlarm(alarmModel: AlarmModel) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val calendar = Calendar.getInstance()

        repeat(times = 7) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 in alarmModel.alarmDays) {
                val calendarUniqueId =
                    alarmModel.createdAt.toInt() + calendar.get(Calendar.DAY_OF_WEEK) - 1
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    calendarUniqueId,
                    alarmIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.cancel(pendingIntent)
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
    }

}