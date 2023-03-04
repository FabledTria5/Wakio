package dev.fabled.wakio

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WakioApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(applicationContext)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "alarm_service_id",
            "alarm_channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description =
            "This is channel is used to show user notifications when he is closing alarm"

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

}