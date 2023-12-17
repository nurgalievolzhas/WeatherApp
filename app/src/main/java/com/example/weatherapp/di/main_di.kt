package com.example.weatherapp.di

import com.example.weatherapp.common.di.baseModules

val mainModules = arrayListOf(
    baseModules,
    weatherNetworkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)