package dev.fabled.repository.android_utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.domain.utils.ApplicationUtil
import javax.inject.Inject

class AndroidApplicationUtil @Inject constructor(
    @ApplicationContext val context: Context
) : ApplicationUtil {

    private val connectivityManager by lazy {
        context.getSystemService(ConnectivityManager::class.java)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isInternetAvailable(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}