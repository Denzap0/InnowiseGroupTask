package com.example.innowisegrouptask.service.mappers

import com.example.innowisegrouptask.service.dataclasses.OneCallWeatherPresenter
import com.example.innowisegrouptask.ui.data.OneCallWeatherUi

class OneCallWeatherUiMapper : (List<OneCallWeatherPresenter>) -> List<OneCallWeatherUi> {
    override fun invoke(p1: List<OneCallWeatherPresenter>): List<OneCallWeatherUi> =
        p1.map { item ->
            OneCallWeatherUi(
                item.coordinates,
                item.time * 1000,
                item.temperature,
                item.mainDescription,
                item.humidity,
                item.airPressure,
                item.airSpeed,
                item.airDirection
            )
        }

}