package com.example.innowisegrouptask.ui.listeners

import com.example.innowisegrouptask.ui.data.OneCallWeatherUi

interface ForecastFragmentListener {
    fun setWeatherList(weatherList: List<OneCallWeatherUi>)
}