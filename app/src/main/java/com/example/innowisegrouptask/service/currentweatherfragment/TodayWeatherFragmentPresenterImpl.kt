package com.example.innowisegrouptask.service.currentweatherfragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.example.innowisegrouptask.data.currentweatherapi.CurrentWeatherAPI
import com.example.innowisegrouptask.data.currentweatherapi.CurrentWeatherImpl
import com.example.innowisegrouptask.service.mappers.CurrentWeatherUiMapper
import com.example.innowisegrouptask.ui.listeners.TodayWeatherFragmentListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class TodayWeatherFragmentPresenterImpl(
    private val todayWeatherFragmentListener: TodayWeatherFragmentListener
) : TodayWeatherFragmentPresenter {
    private val currentWeatherAPI: CurrentWeatherAPI by lazy {
        CurrentWeatherImpl()
    }
    private val currentWeatherUiMapper by lazy {
        CurrentWeatherUiMapper()
    }

    override fun getCurrentWeather(coordinates: Pair<Double, Double>) {
        currentWeatherAPI.getCurrentWeather(Pair(coordinates.first,coordinates.second))
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> currentWeatherUiMapper.invoke(data)}
            .subscribe({
                todayWeatherFragmentListener.setCurrentWeather(it)
            },{
                Throwable(it)
            })
    }


}