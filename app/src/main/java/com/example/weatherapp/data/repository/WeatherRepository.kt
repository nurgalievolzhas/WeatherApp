package com.example.weatherapp.data.repository

import com.example.weatherapp.data.entity.CurrentWeatherResponse
import com.example.weatherapp.utils.network.ResultApi

interface WeatherRepositoryApi {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): ResultApi<CurrentWeatherResponse>
}