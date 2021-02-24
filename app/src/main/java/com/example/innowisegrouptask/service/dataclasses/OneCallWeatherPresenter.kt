package com.example.innowisegrouptask.service.dataclasses

class OneCallWeatherPresenter (
    val coordinates : Pair<Double,Double>,
    val time : Long,
    val temperature : Int,
    val mainDescription : String,
    val humidity : Int,
    val airPressure : Int,
    val airSpeed : Int,
    val airDirection : String
)