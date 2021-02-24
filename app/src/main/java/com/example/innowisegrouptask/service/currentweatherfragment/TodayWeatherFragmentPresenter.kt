package com.example.innowisegrouptask.service.currentweatherfragment

interface TodayWeatherFragmentPresenter {
    fun getCurrentWeather(coordinates: Pair<Double, Double>)
}