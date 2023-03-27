package dev.fabled.repository.android_utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.utils.AlarmUtil
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmUtil @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmUtil {

    private val alarmManager by lazy { context.getSystemService(AlarmManager::class.java) }

    override fun setAlarm(alarmModel: AlarmModel, uniqueId: Int) {
        val alarmIntent = Intent("dev.fabled.wakio.RECEIVE_ALARM").apply {
            putExtra("ALARM_SOUND_TAG", alarmModel.alarmSoundTag)
            putExtra("ALARM_VOLUME", alarmModel.alarmVolume)
            putExtra("IS_VIBRATION_ENABLED", alarmModel.isVibrationEnabled)
        }

        val calendar = Calendar.getInstance().apply {
            val currentTime = timeInMillis

            set(Calendar.HOUR_OF_DAY, alarmModel.alarmHour)
            set(Calendar.MINUTE, alarmModel.alarmMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (currentTime > timeInMillis) {
                Timber.d("Current day will be ignored")
                add(Calendar.DAY_OF_WEEK, 1)
            } else {
                Timber.d("Current day will not be ignored")
            }
        }

        repeat(times = 7) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 in alarmModel.alarmDays) {
//                val pendingIntent = PendingIntent.getBroadcast(
//                    context,
//                    uniqueId,
//                    alarmIntent,
//                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                )
//
//                alarmManager.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
//                    pendingIntent
//                )

                Timber.d("Alarm with id $uniqueId has been scheduled for ${calendar.time}")
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
    }

    override fun removeAlarm(alarmModel: AlarmModel) {
        val alarmIntent = Intent("dev.fabled.wakio.RECEIVE_ALARM")
        val calendar = Calendar.getInstance()

        repeat(times = 7) {
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 in alarmModel.alarmDays) {
                val uniqueId =
                    alarmModel.createdAt.toInt() + calendar.get(Calendar.DAY_OF_WEEK) - 1
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    uniqueId,
                    alarmIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.cancel(pendingIntent)
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
    }

}