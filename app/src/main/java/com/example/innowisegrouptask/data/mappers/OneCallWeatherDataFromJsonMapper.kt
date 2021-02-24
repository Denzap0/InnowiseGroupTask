package com.example.innowisegrouptask.data.mappers

import com.example.innowisegrouptask.data.dataclasses.OneCallWeatherItemData
import org.json.JSONObject

class OneCallWeatherDataFromJsonMapper : (String) -> List<OneCallWeatherItemData> {
    private val windDirectionDegreesToStringMapper by lazy {
        WindDirectionDegreesToStringMapper()
    }

    override fun invoke(json: String): List<OneCallWeatherItemData> {
        val hoursList = mutableListOf<OneCallWeatherItemData>()
        val jsonObject = JSONObject(json)
        val hourly = jsonObject.getJSONArray("hourly")
        for (i in 0 until hourly.length()) {
            val hourJsonObject = hourly.getJSONObject(i)
            hoursList.add(
                OneCallWeatherItemData(
                    coordinates = Pair(jsonObject.getDouble("lat"), jsonObject.getDouble("lon")),
                    temperature = (hourJsonObject.getInt("temp") - 273.15).toInt(),
                    mainDescription = hourJsonObject.getJSONArray("weather").getJSONObject(0)
                        .getString("main"),
                    time = hourJsonObject.getLong("dt"),
                    humidity = hourJsonObject.getInt("humidity"),
                    airPressure = hourJsonObject.getInt("pressure"),
                    airSpeed = hourJsonObject.getInt("wind_speed"),
                    airDirection = windDirectionDegreesToStringMapper.invoke(hourJsonObject.getInt("wind_deg"))
                )
            )

        }
        return hoursList
    }
}