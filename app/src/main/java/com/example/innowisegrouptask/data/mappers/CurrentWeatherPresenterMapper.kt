package com.example.innowisegrouptask.data.mappers

import com.example.innowisegrouptask.data.dataclasses.CurrentWeatherData
import com.example.innowisegrouptask.service.dataclasses.CurrentWeatherPresenter

class CurrentWeatherPresenterMapper : (CurrentWeatherData) -> CurrentWeatherPresenter {
    override fun invoke(currentWeatherData: CurrentWeatherData): CurrentWeatherPresenter =
        CurrentWeatherPresenter(
            currentWeatherData.coordinates,
            currentWeatherData.location,
            currentWeatherData.temperature,
            currentWeatherData.time,
            currentWeatherData.mainDescription,
            currentWeatherData.humidity,
            currentWeatherData.airPressure,
            currentWeatherData.airSpeed,
            currentWeatherData.airDirection
        )
}