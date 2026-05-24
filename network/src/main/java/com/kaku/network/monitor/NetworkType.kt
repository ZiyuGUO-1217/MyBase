package com.kaku.network.monitor

sealed interface NetworkType {
    data object Wifi : NetworkType {
        override val description: String
            get() = "WiFi"
    }

    data object Cellular : NetworkType {
        override val description: String
            get() = "Cellular"
    }

    data object Offline : NetworkType {
        override val description: String
            get() = "Offline"
    }

    data object Other : NetworkType {
        override val description: String
            get() = "Other"
    }

    val description: String
    val isOnline: Boolean
        get() = this != Offline
}
