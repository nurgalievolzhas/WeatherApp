package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.mainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WeatherApp)
            modules(mainModules)
        }
    }
}