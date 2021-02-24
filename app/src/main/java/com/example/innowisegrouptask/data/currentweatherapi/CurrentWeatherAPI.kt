package com.example.innowisegrouptask.data.currentweatherapi

import com.example.innowisegrouptask.service.dataclasses.CurrentWeatherPresenter
import io.reactivex.rxjava3.core.Single

interface CurrentWeatherAPI {
    fun getCurrentWeather(coordPair : Pair<Double,Double>) : Single<CurrentWeatherPresenter>
}