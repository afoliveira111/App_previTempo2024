package com.example.app_previtempo2024.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    val by: String,
    @SerializedName("weather") val validKey: Boolean,
    val results: WeatherResults,
)

data class WeatherResults(
    val temp: Int,
    val date: String,
    val time: String,
    val description: String,
    val currently: String,
    val city: String,
    val humidity: Int,
    val cloudiness: Int,
    val rain: Double,
    @SerializedName("wind_speedy") val windSpeedy: String,
    val sunrise: String,
    val sunset: String,
    val forecast: List<Forecast>,
)

data class Forecast(
    val date: String,
    val weekday: String,
    val max: Int,
    val min: Int,
    val description: String,
    val condition: String,
    @SerializedName("rain_probability") val rainProbability: Int,
    @SerializedName("wind_speedy") val windSpeedy: String
)
