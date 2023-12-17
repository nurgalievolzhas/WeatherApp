package com.example.weatherapp.presentation.weather_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.common.ext.onBackPressed
import com.example.weatherapp.common.ext.toast
import com.example.weatherapp.databinding.FragmentWeatherInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherInfoFragment : Fragment() {

    private val viewModel: WeatherInfoViewModel by viewModel()
    private var _binding: FragmentWeatherInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadWeatherInfo(arguments)
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        weatherInfo.observe(viewLifecycleOwner) {
            with(binding) {
                tvDistrictName.text = it.name
                tvRainChance.text = it.weather.first().description
                tvTemperature.text = it.main.temp.toInt().toString() + "°C"
                tvFeelsLike.text = context?.getString(
                    R.string.temperature_feels_like,
                    it.main.feelsLike.toInt().toString() + "°C"
                )
                when (it.weather.first().main.lowercase()) {
                    "mist" -> {
                        ivWeather.setImageDrawable(context?.getDrawable(R.drawable.mist))
                    }
                    "rain" -> {
                        ivWeather.setImageDrawable(context?.getDrawable(R.drawable.rain))
                    }
                    "snow" -> {
                        ivWeather.setImageDrawable(context?.getDrawable(R.drawable.snow))
                    }
                    "clouds" -> {
                        ivWeather.setImageDrawable(context?.getDrawable(R.drawable.clound))
                    }
                    "clear" -> {
                        ivWeather.setImageDrawable(context?.getDrawable(R.drawable.clound))
                    }
                }
            }
        }
        cityName.observe(viewLifecycleOwner) {
            binding.tvCityName.text = it
        }
        loading.observe(viewLifecycleOwner) {
            binding.progressBar.root.isVisible = it
        }
        error.observe(viewLifecycleOwner) {
            context?.toast(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}