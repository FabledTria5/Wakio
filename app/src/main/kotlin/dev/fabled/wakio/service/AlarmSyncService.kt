package dev.fabled.wakio.service

import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import dev.fabled.common.code.service.BaseService
import dev.fabled.repository.repository.AlarmsRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmSyncService : BaseService() {

    @Inject
    lateinit var alarmsRepositoryImpl: AlarmsRepositoryImpl

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {

        }
        return START_STICKY
    }

}