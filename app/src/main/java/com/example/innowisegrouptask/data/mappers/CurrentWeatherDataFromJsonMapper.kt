package com.example.innowisegrouptask.data.mappers

import com.example.innowisegrouptask.data.dataclasses.CurrentWeatherData
import org.json.JSONObject

class CurrentWeatherDataFromJsonMapper : (String) -> CurrentWeatherData {
    override fun invoke(json: String): CurrentWeatherData {
        val windDirectionDegreesToStringMapper = WindDirectionDegreesToStringMapper()
        val jsonObject = JSONObject(json)
        val mainJSONObject = jsonObject.getJSONObject("main")
        val windJSONObject = jsonObject.getJSONObject("wind")
        val weatherJSONObject = jsonObject.getJSONArray("weather")
        val coordinatesJSONObject = jsonObject.getJSONObject("coord")
        return CurrentWeatherData(
            location = jsonObject.getString("name"),
            temperature = (mainJSONObject.getDouble("temp") - 273.15).toInt(),
            mainDescription = weatherJSONObject.getJSONObject(0).getString("main"),
            humidity = mainJSONObject.getInt("humidity"),
            time = jsonObject.getLong("dt") * 1000,
            airPressure = jsonObject.getJSONObject("main").getInt("pressure"),
            airSpeed = windJSONObject.getInt("speed"),
            airDirection = windDirectionDegreesToStringMapper.invoke(windJSONObject.getInt("deg")),
            coordinates = Pair(coordinatesJSONObject.getDouble("lon"),coordinatesJSONObject.getDouble("lat"))
        )
    }
}