package com.example.innowisegrouptask.data.currentweatherapi

import okhttp3.Request

interface CurrentWeatherRequestFactory {
    fun getCurrentWeatherRequest(coordPair: Pair<Double,Double>) : Request
}