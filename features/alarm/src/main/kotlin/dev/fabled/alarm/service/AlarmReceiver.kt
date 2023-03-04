package dev.fabled.alarm.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION = "dev.fabled.alarm.broadcast"
    }

    override fun onReceive(content: Context, intent: Intent?) {
        content.startService(Intent(content, AlarmService::class.java))
    }

}