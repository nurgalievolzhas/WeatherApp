package com.example.weatherapp.di

import com.example.weatherapp.presentation.choose_city.CityChooserViewModel
import com.example.weatherapp.presentation.weather_info.WeatherInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherInfoViewModel(weatherRepository = get())
    }
    viewModel {
        CityChooserViewModel()
    }
}