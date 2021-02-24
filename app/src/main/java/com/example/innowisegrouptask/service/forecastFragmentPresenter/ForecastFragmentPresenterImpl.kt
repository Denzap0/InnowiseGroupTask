package com.example.innowisegrouptask.service.forecastFragmentPresenter

import android.util.Log
import com.example.innowisegrouptask.data.onecallweatherapi.OneCallAPI
import com.example.innowisegrouptask.data.onecallweatherapi.OneCallAPIImpl
import com.example.innowisegrouptask.service.mappers.OneCallWeatherUiMapper
import com.example.innowisegrouptask.ui.listeners.ForecastFragmentListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*

class ForecastFragmentPresenterImpl(private val forecastFragmentListener: ForecastFragmentListener) : ForecastFragmentPresenter {
    private val oneCallAPI : OneCallAPI by lazy {
        OneCallAPIImpl()
    }
    private val oneCallWeatherUiMapper by lazy {
        OneCallWeatherUiMapper()
    }
    override fun fetchWeatherList(coordPair: Pair<Double, Double>) {
        oneCallAPI.getOneCallWeather(coordPair)
            .map {data ->  oneCallWeatherUiMapper.invoke(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({list ->
                list.forEach { item ->
                    Log.d("AAAAAAA", SimpleDateFormat("hh:mm").format( item.time))
                }
                forecastFragmentListener.setWeatherList(list)
            },{
                Throwable(it)
            })
    }
}