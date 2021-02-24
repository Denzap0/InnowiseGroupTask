package com.example.innowisegrouptask.data.currentweatherapi

import com.example.innowisegrouptask.BuildConfig
import okhttp3.Request

private const val API_KEY = BuildConfig.MY_API_KEY
private const val WEATHER_URL =
    "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s"

class RequestFactoryImpl : CurrentWeatherRequestFactory {
    override fun getCurrentWeatherRequest(coordPair: Pair<Double, Double>) =
        Request.Builder().url(WEATHER_URL.format(coordPair.first, coordPair.second, API_KEY))
            .build()

}