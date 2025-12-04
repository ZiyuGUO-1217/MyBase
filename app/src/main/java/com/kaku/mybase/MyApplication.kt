package com.kaku.mybase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

private const val TIMBER_TAG = "MyTimber_"

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    message: String,
                    t: Throwable?,
                ) {
                    super.log(priority, TIMBER_TAG + tag, message, t)
                }
            })
        }
    }
}
