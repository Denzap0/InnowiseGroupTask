package com.example.innowisegrouptask.data.onecallweatherapi

import com.example.innowisegrouptask.service.dataclasses.OneCallWeatherPresenter
import io.reactivex.rxjava3.core.Single

interface OneCallAPI {
    fun getOneCallWeather(coordPair: Pair<Double, Double>): Single<List<OneCallWeatherPresenter>>
}