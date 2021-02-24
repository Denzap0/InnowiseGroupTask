package com.example.innowisegrouptask.data.onecallweatherapi

import com.example.innowisegrouptask.data.mappers.OneCallWeatherDataFromJsonMapper
import com.example.innowisegrouptask.data.mappers.OneCallWeatherPresenterMapper
import com.example.innowisegrouptask.service.dataclasses.OneCallWeatherPresenter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class OneCallAPIImpl : OneCallAPI {
    private val okHttpClient by lazy{
        OkHttpClient()
    }
    private val requestFactory : OneCallRequestFactory by lazy {
        OneCallRequestFactoryImpl()
    }

    private val oneCallWeatherDataFromJsonMapper by lazy {
        OneCallWeatherDataFromJsonMapper()
    }
    private val oneCallWeatherPresenterMapper by lazy {
        OneCallWeatherPresenterMapper()
    }
    override fun getOneCallWeather(coordPair: Pair<Double, Double>): Single<List<OneCallWeatherPresenter>> {
        val request = requestFactory.getOneCallRequest(coordPair)
        return Single.create<String> { emitter ->
            val response = okHttpClient.newCall(request).execute()
            if(response.isSuccessful){
                if (response.body != null){
                    emitter.onSuccess((response.body as ResponseBody).string())
                }else{
                    emitter.onError(Throwable("EMPTY ONE CALL WEATHER RESPONSE BODY"))
                }
            }else{
                emitter.onError(Throwable("ONE CALL API ERROR"))
            }
        }.map { json -> oneCallWeatherDataFromJsonMapper.invoke(json) }
            .map {data ->  oneCallWeatherPresenterMapper.invoke(data) }
            .subscribeOn(Schedulers.io())
    }
}