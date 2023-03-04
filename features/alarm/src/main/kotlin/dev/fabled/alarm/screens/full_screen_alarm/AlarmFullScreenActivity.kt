package dev.fabled.alarm.screens.full_screen_alarm

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.fabled.alarm.service.AlarmService

@AndroidEntryPoint
class AlarmFullScreenActivity : ComponentActivity() {

    private lateinit var alarmService: AlarmService

    private val alarmServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as AlarmService.LocalBinder
            alarmService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) = Unit
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOnLockedScreen()

        setContent {
            WorkingAlarmScreen(
                onCloseScreenClicked = {
                    alarmService.stopService()
                    finish()
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()

        Intent(this, AlarmService::class.java).also { intent ->
            bindService(intent, alarmServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()

        unbindService(alarmServiceConnection)
    }

    @Suppress("DEPRECATION")
    private fun showOnLockedScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }

}