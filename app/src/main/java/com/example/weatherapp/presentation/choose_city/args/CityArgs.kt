package com.example.weatherapp.presentation.choose_city.args

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityArgs(
    val name: String,
    val latitude: Double,
    val longitude: Double
): Parcelable
