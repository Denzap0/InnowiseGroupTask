package com.example.innowisegrouptask.data.currentweatherapi

import com.example.innowisegrouptask.data.dataclasses.CurrentWeatherData
import com.example.innowisegrouptask.data.mappers.CurrentWeatherDataFromJsonMapper
import com.example.innowisegrouptask.data.mappers.CurrentWeatherPresenterMapper
import com.example.innowisegrouptask.service.dataclasses.CurrentWeatherPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class CurrentWeatherImpl : CurrentWeatherAPI {
    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient()
    }
    private val requestFactory : CurrentWeatherRequestFactory by lazy {
        RequestFactoryImpl()
    }
    private val currentWeatherDataFromJsonMapper by lazy {
        CurrentWeatherDataFromJsonMapper()
    }

    private val presenterMapper by lazy {
        CurrentWeatherPresenterMapper()
    }

    override fun getCurrentWeather(coordPair: Pair<Double, Double>): Single<CurrentWeatherPresenter> {
        val request = requestFactory.getCurrentWeatherRequest(coordPair)

        return Single.create<String> { emitter ->
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful){
                if (response.body != null){
                    emitter.onSuccess((response.body as ResponseBody).string())
                }else{
                    emitter.onError(Throwable("EMPTY CURRENT WEATHER RESPONSE BODY"))
                }
            }else{
                emitter.onError(Throwable("CURRENT WEATHER RESPONSE ERROR ${response.code}"))
            }
        }.map { json -> currentWeatherDataFromJsonMapper.invoke(json) }
            .map { currentWeatherData -> presenterMapper.invoke(currentWeatherData) }
            .subscribeOn(Schedulers.io())
    }
}