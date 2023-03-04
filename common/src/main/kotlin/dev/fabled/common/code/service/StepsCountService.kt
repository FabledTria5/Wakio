package dev.fabled.common.code.service

import android.content.Intent
import android.os.IBinder
import androidx.health.connect.client.HealthConnectClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepsCountService : BaseService() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (HealthConnectClient.isProviderAvailable(applicationContext)) connectFitness()

        return START_STICKY
    }

    private fun connectFitness() {
        val healthConnectClient = HealthConnectClient.getOrCreate(this)
    }

}