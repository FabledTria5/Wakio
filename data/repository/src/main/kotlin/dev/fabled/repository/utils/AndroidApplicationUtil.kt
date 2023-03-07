package dev.fabled.repository.utils

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.domain.model.AlarmModel
import dev.fabled.domain.utils.ApplicationUtil
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class AndroidApplicationUtil @Inject constructor(
    @ApplicationContext val context: Context
) : ApplicationUtil {

    private val connectivityManager by lazy {
        context.getSystemService(ConnectivityManager::class.java)
    }

    private val alarmManager by lazy {
        context.getSystemService(AlarmManager::class.java)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isInternetAvailable(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun setupAlarm(alarmModel: AlarmModel, alarmId: Int) {
        alarmModel.alarmDays.forEach { dayValue ->
            val alarmIntent = Intent("dev.fabled.alarm.broadcast").apply {
                putExtra("ALARM_SOUND_TAG", alarmModel.alarmSoundTag)
                putExtra("ALARM_VOLUME", alarmModel.alarmVolume)
                putExtra("IS_VIBRATION_ENABLED", alarmModel.isVibrationEnabled)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmId,
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, dayValue)
                set(Calendar.HOUR, alarmModel.alarmHour)
                set(Calendar.MINUTE, alarmModel.alarmMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            Timber.d("Alarm will be set for calendar value $calendar")

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                7.days.inWholeMilliseconds,
                pendingIntent
            )

            Timber.d("Alarm has been set for day with value $dayValue")
        }
    }

    override fun removeAlarm(alarmModel: AlarmModel) {
        alarmModel.alarmDays.forEach { dayValue ->
            val alarmIntent = Intent("dev.fabled.alarm.broadcast")
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmModel.alarmId,
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)

            Timber.d("Alarm with id ${alarmModel.alarmId} has been removed for day with value $dayValue")
        }
    }
}