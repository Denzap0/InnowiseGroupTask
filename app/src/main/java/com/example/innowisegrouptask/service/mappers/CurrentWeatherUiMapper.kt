package com.example.innowisegrouptask.service.mappers

import com.example.innowisegrouptask.service.dataclasses.CurrentWeatherPresenter
import com.example.innowisegrouptask.ui.data.CurrentWeatherUi

class CurrentWeatherUiMapper : (CurrentWeatherPresenter) -> CurrentWeatherUi {
    override fun invoke(p1: CurrentWeatherPresenter): CurrentWeatherUi =
        CurrentWeatherUi(p1.coordinates,p1.location,p1.time,p1.temperature,p1.mainDescription,p1.humidity,p1.airPressure,p1.airSpeed,p1.airDirection)
}