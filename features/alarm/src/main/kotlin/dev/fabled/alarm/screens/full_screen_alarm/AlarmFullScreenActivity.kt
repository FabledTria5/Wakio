package dev.fabled.alarm.screens.full_screen_alarm

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.fabled.alarm.service.AlarmService
import dev.fabled.common.ui.theme.WakioTheme

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        showOnLockedScreen()

        setContent {
            WakioTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
                }

                Surface(color = MaterialTheme.colorScheme.background) {
                    WorkingAlarmScreen(
                        onCloseScreenClicked = {
                            alarmService.stop()
                            finish()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

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