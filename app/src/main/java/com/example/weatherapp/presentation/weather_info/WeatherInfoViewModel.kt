package com.example.weatherapp.presentation.weather_info

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.constants.ARGConstants.ARG_CITY_DATA
import com.example.weatherapp.common.constants.CommonConstants
import com.example.weatherapp.data.entity.CurrentWeatherResponse
import com.example.weatherapp.data.repository.WeatherRepositoryApi
import com.example.weatherapp.presentation.choose_city.args.CityArgs
import com.example.weatherapp.utils.network.ResultApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherInfoViewModel(
    private val weatherRepository: WeatherRepositoryApi
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<CurrentWeatherResponse>()
    val weatherInfo: LiveData<CurrentWeatherResponse> get() = _weatherInfo

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> get() = _cityName

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun loadWeatherInfo(arguments: Bundle?) {
        arguments?.let {
            val cityData: CityArgs? = it.getParcelable(ARG_CITY_DATA)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    cityData?.let {
                        _cityName.postValue(it.name)
                        _loading.postValue(true)
                        val result = weatherRepository.getCurrentWeather(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                        when (result) {
                            is ResultApi.Success -> {
                                if (result.data != null) {
                                    _weatherInfo.postValue(result.data!!)
                                } else {
                                    _error.postValue("Unknown error")
                                }
                            }
                            is ResultApi.HttpError<*> -> {
                                _error.postValue(result.error as? String ?: CommonConstants.EMPTY)
                            }
                        }

                        _loading.postValue(false)
                    }
                } catch (e: Exception) {

                    _loading.postValue(false)
                    _error.postValue(e.message)
                }
            }
        }
    }
}