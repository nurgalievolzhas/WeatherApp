package com.example.weatherapp.di

import com.example.weatherapp.data.repository.WeatherRepositoryApi
import com.example.weatherapp.domain.repository_impl.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepositoryImpl(weatherApi = get()) as WeatherRepositoryApi }
}