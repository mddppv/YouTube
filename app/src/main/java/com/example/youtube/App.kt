package com.example.youtube

import android.app.Application
import com.example.youtube.di.youTubeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(youTubeModule)
        }
    }
}