package com.example.innowisegrouptask.data.onecallweatherapi

import com.example.innowisegrouptask.BuildConfig
import okhttp3.Request

private const val API_KEY = BuildConfig.MY_API_KEY
private const val ONE_CALL_WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hour&appid=%s"

class OneCallRequestFactoryImpl : OneCallRequestFactory {
    override fun getOneCallRequest(coordinates: Pair<Double, Double>): Request =
        Request.Builder().url(ONE_CALL_WEATHER_URL.format(coordinates.first,coordinates.second,
            API_KEY)).build()

}