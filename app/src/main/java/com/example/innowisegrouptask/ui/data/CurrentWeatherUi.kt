package com.example.innowisegrouptask.ui.data

class CurrentWeatherUi (
    val coordinates : Pair<Double,Double>,
    val location : String,
    val time : Long,
    val temperature : Int,
    val mainDescription : String,
    val humidity : Int,
    val airPressure : Int,
    val airSpeed : Int,
    val airDirection : String
)