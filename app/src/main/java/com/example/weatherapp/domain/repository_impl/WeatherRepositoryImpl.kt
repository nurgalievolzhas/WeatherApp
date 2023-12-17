package com.example.weatherapp.domain.repository_impl

import com.example.weatherapp.data.entity.CurrentWeatherResponse
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryApi
import com.example.weatherapp.utils.network.ResultApi
import com.example.weatherapp.utils.network.safeApiCall

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepositoryApi {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): ResultApi<CurrentWeatherResponse> =
        safeApiCall {
            weatherApi.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
        }
}