package com.example.app_previtempo2024.model


data class WeatherData(
    val city: String,
    val temperature: Int,
    val humidity: Int,
    val description: String,
    val isDay: Boolean,
    val date: String,
    val time: String,
    val sunrise: String,
    val sunset: String,
    val precipitationProbability: Int,
    val windSpeedy: String,
)


