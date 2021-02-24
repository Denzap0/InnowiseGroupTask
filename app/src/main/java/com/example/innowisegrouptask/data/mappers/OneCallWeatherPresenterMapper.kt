package com.example.innowisegrouptask.data.mappers

import com.example.innowisegrouptask.data.dataclasses.OneCallWeatherItemData
import com.example.innowisegrouptask.service.dataclasses.OneCallWeatherPresenter

class OneCallWeatherPresenterMapper :
        (List<OneCallWeatherItemData>) -> List<OneCallWeatherPresenter> {
    override fun invoke(dataList: List<OneCallWeatherItemData>): List<OneCallWeatherPresenter> =
        dataList.map { data ->
            OneCallWeatherPresenter(
                data.coordinates,
                data.time,
                data.temperature,
                data.mainDescription,
                data.humidity,
                data.airPressure,
                data.airSpeed,
                data.airDirection
            )
        }

}