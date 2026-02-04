package com.shailesh.icewarptask.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class IceWarpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}