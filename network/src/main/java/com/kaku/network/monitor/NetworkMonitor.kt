package com.kaku.network.monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.stateIn

interface NetworkMonitor {
    val networkType: StateFlow<NetworkType>
}

class ConnectivityManagerNetworkMonitor @Inject constructor(
    private val context: Context,
    applicationScope: CoroutineScope,
) : NetworkMonitor {
    override val networkType: StateFlow<NetworkType> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager == null) {
            trySend(NetworkType.Offline)
            close()
            return@callbackFlow
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(connectivityManager.getNetworkType(network))
            }

            override fun onLost(network: Network) {
                trySend(NetworkType.Offline)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(connectivityManager.getNetworkType(network))
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        channel.trySend(connectivityManager.getCurrentNetworkType())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
        .conflate()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NetworkType.Cellular,
        )

    private fun ConnectivityManager.getNetworkType(network: Network): NetworkType {
        val capabilities = getNetworkCapabilities(network) ?: return NetworkType.Offline
        return getNetworkType(capabilities)
    }

    private fun ConnectivityManager.getCurrentNetworkType(): NetworkType {
        val capabilities = activeNetwork?.let(::getNetworkCapabilities)
        return capabilities?.let(::getNetworkType) ?: NetworkType.Offline
    }

    private fun getNetworkType(capabilities: NetworkCapabilities): NetworkType {
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.Wifi
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.Cellular
            else -> NetworkType.Other
        }
    }
}
