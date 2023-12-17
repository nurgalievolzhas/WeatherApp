package com.example.weatherapp.presentation.choose_city.mapper

import com.example.weatherapp.presentation.choose_city.dvo.CityDvo
import com.example.weatherapp.presentation.choose_city.args.CityArgs

class CityChooserMapper {

    fun mapToDvo(
        clickAction: (CityArgs) -> Unit
    ) = mutableListOf<CityDvo>().apply {
        addAll(
            listOf(
                CityDvo(
                    title = "Almaty",
                    cityData = CityArgs(
                        name = "Almaty",
                        latitude = 43.238949,
                        longitude = 76.889709
                    ),
                    onClick = clickAction
                ),
                CityDvo(
                    title = "Astana",
                    cityData = CityArgs(
                        name = "Astana",
                        latitude = 51.169392,
                        longitude = 71.449074
                    ),
                    onClick = clickAction
                ),
                CityDvo(
                    title = "Pavlodar",
                    cityData = CityArgs(
                        name = "Pavlodar",
                        latitude = 52.287303,
                        longitude = 76.967402
                    ),
                    onClick = clickAction
                ),
            )
        )
    }
}

