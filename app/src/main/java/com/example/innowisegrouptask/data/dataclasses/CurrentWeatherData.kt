package com.example.innowisegrouptask.data.dataclasses

import android.location.Geocoder

class CurrentWeatherData (
    val coordinates : Pair<Double,Double>,
    val location : String,
    val temperature : Int,
    val time : Long,
    val mainDescription : String,
    val humidity : Int,
    val airPressure : Int,
    val airSpeed : Int,
    val airDirection : String
)