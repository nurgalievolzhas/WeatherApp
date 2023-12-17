package com.example.weatherapp.data.remote

import com.example.weatherapp.data.entity.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("https://api.openweathermap.org/data/2.5/weather?appid=070c53660d1ab31a6a77d1b1a6a83ab5&units=metric&lang=ru")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ) : CurrentWeatherResponse

}