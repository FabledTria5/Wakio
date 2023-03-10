package dev.fabled.alarm.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("Receiver starts...")

        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(
                AlarmService.ALARM_SOUND_TAG,
                intent?.getStringExtra(AlarmService.ALARM_SOUND_TAG).orEmpty()
            )
            putExtra(
                AlarmService.ALARM_VOLUME,
                intent?.getFloatExtra(AlarmService.ALARM_VOLUME, .5f)
            )
            putExtra(
                AlarmService.IS_VIBRATION_ENABLED,
                intent?.getBooleanExtra(AlarmService.IS_VIBRATION_ENABLED, true)
            )
        }

        context.startForegroundService(serviceIntent)

        Timber.d("Receiver work ends")
    }

}