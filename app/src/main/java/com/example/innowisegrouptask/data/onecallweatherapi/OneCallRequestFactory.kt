package com.example.innowisegrouptask.data.onecallweatherapi

import okhttp3.Request

interface OneCallRequestFactory {
    fun getOneCallRequest(coordinates: Pair<Double, Double>): Request
}