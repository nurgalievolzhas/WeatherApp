package com.example.weatherapp.common.di

import com.example.weatherapp.utils.network.createRetrofitOkHttpClient
import org.koin.dsl.module

val baseModules = module {
    factory { createRetrofitOkHttpClient() }
}