package com.example.innowisegrouptask.service.forecastFragmentPresenter

interface ForecastFragmentPresenter {
    fun fetchWeatherList(coordPair: Pair<Double,Double>)
}