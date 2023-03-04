package dev.fabled.alarm.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import dev.fabled.alarm.R
import dev.fabled.alarm.screens.full_screen_alarm.AlarmFullScreenActivity
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@SuppressLint("MissingPermission")
class AlarmService : Service() {

    companion object {
        private const val INTENT_REQUEST_CODE = 0
    }

    private val binder = LocalBinder()

    private val vibrator by lazy { getSystemService(Vibrator::class.java) }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createFullScreenAlarm()
        powerScreenUp()
        startVibrate()

        return START_STICKY
    }

    private fun createFullScreenAlarm() {
        val fullScreenIntent = Intent(baseContext, AlarmFullScreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            baseContext,
            INTENT_REQUEST_CODE,
            fullScreenIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(baseContext, "alarm_service_id")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Alarm now")
            .setContentText("Content text")
            .setAutoCancel(true)
            .setFullScreenIntent(pendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()

        startForeground(Random.nextInt(from = 1, until = 1000), notification)
    }

    @Suppress("DEPRECATION")
    private fun powerScreenUp() {
        val powerManager = getSystemService(PowerManager::class.java)
        val wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK
                    or PowerManager.ACQUIRE_CAUSES_WAKEUP
                    or PowerManager.ON_AFTER_RELEASE,
            "wakio::wakelock"
        )
        wakeLock.acquire(5.seconds.inWholeMilliseconds)
    }

    private fun startVibrate() {
        val vibrationPattern = longArrayOf(0, 10, 200, 500, 700, 1000, 300, 200, 50, 10)
        val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, 0)

        vibrator.vibrate(vibrationEffect)
    }

    fun stopService() {
        vibrator.cancel()
        stopSelf()
    }

    inner class LocalBinder : Binder() {
        fun getService(): AlarmService = this@AlarmService
    }

}