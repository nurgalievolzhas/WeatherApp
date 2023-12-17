package com.example.weatherapp.di

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.utils.network.createWebService
import org.koin.dsl.module

val weatherNetworkModule = module {
    single {
        createWebService<WeatherApi>(
            okHttpClient = get(),
            baseUrl = "https://api.openweathermap.org/data/2.5/weather/"
        )
    }
}