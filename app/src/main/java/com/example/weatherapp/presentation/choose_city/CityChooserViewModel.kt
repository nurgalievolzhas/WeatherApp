package com.example.weatherapp.presentation.choose_city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.repository.WeatherRepositoryApi
import com.example.weatherapp.presentation.choose_city.args.CityArgs
import com.example.weatherapp.presentation.choose_city.dvo.CityDvo
import com.example.weatherapp.presentation.choose_city.mapper.CityChooserMapper

class CityChooserViewModel(
    private val weatherRepository: WeatherRepositoryApi,
    private val mapper: CityChooserMapper = CityChooserMapper()
): ViewModel() {

    private val _cities = MutableLiveData<List<CityDvo>>()
    val cities: LiveData<List<CityDvo>> get() = _cities

    private val _navigateToWeatherInfo = MutableLiveData<CityArgs>()
    val navigateToWeatherInfo: LiveData<CityArgs> get() = _navigateToWeatherInfo

    fun loadCities() {
        _cities.value = mapper.mapToDvo {
            _navigateToWeatherInfo.value = it
        }
    }

}