package dev.fabled.alarm.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RawRes
import androidx.core.app.NotificationCompat
import dev.fabled.alarm.R
import dev.fabled.alarm.model.AlarmSoundModel
import dev.fabled.alarm.screens.full_screen_alarm.AlarmFullScreenActivity
import dev.fabled.alarm.utils.playRawResAudio
import kotlin.time.Duration.Companion.seconds

@SuppressLint("MissingPermission")
class AlarmService : Service() {

    companion object {
        private const val INTENT_REQUEST_CODE = 0
        private const val notificationId = 513341

        const val ALARM_SOUND_TAG = "ALARM_SOUND_TAG"
        const val ALARM_VOLUME = "ALARM_VOLUME"
        const val IS_VIBRATION_ENABLED = "IS_VIBRATION_ENABLED"
    }

    private val binder = LocalBinder()

    private val vibrator by lazy { getSystemService(Vibrator::class.java) }

    private val notificationManager by lazy { getSystemService(NotificationManager::class.java) }

    private val audioManager by lazy { getSystemService(AudioManager::class.java) }

    private val mediaPlayer by lazy { MediaPlayer() }

    private var deviceVolume = 0

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val alarmSoundModel = intent.getStringExtra(ALARM_SOUND_TAG)?.let { tag ->
            AlarmSoundModel.getByTag(tag)
        } ?: AlarmSoundModel.default()
        val alarmVolume = intent.getFloatExtra(ALARM_VOLUME, .5f)
        val isVibrating = intent.getBooleanExtra(IS_VIBRATION_ENABLED, true)

        createFullScreenAlarm()
        powerScreenUp()

        if (isVibrating) startVibrate()

        startMusic(volume = alarmVolume, sound = alarmSoundModel.audio)

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
            .setSound(null)
            .build()

        notificationManager.notify(notificationId, notification)
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
        val vibrationPattern = longArrayOf(0, 500, 250, 500, 250, 1000)
        val vibrationEffect = VibrationEffect.createWaveform(vibrationPattern, 0)

        vibrator.vibrate(vibrationEffect)
    }

    private fun startMusic(volume: Float, @RawRes sound: Int) {
        if (!audioManager.isVolumeFixed) {
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

            deviceVolume = currentVolume

            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val targetVolume = (maxVolume * volume).toInt()

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume, 0)
        }

        playRawResAudio(this, mediaPlayer, sound)
    }

    fun stopService() {
        vibrator.cancel()

        mediaPlayer.stop()
        mediaPlayer.release()

        if (!audioManager.isVolumeFixed)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, deviceVolume, 0)

        notificationManager.cancel(notificationId)

        stopSelf()
    }

    inner class LocalBinder : Binder() {
        fun getService(): AlarmService = this@AlarmService
    }

}