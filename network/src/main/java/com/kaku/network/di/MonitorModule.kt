package com.kaku.network.di

import android.content.Context
import com.kaku.network.monitor.ConnectivityManagerNetworkMonitor
import com.kaku.network.monitor.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object MonitorModule {
    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
    ): NetworkMonitor = ConnectivityManagerNetworkMonitor(context, scope)
}
