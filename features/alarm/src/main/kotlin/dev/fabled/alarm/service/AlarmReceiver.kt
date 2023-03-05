package dev.fabled.alarm.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION = "dev.fabled.alarm.broadcast"
    }

    override fun onReceive(content: Context, intent: Intent?) {
        val serviceIntent = Intent(content, AlarmService::class.java).apply {
            putExtra(
                AlarmService.ALARM_SOUND_TAG,
                intent?.getStringExtra(AlarmService.ALARM_SOUND_TAG).orEmpty()
            )
            putExtra(
                AlarmService.ALARM_VOLUME,
                intent?.getStringExtra(AlarmService.ALARM_VOLUME).orEmpty()
            )
            putExtra(
                AlarmService.IS_VIBRATION_ENABLED,
                intent?.getStringExtra(AlarmService.IS_VIBRATION_ENABLED).orEmpty()
            )
        }

        content.startService(Intent(content, AlarmService::class.java))
    }

}