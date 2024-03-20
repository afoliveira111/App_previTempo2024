package com.example.app_previtempo2024.action

import com.example.app_previtempo2024.model.WeatherData

data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false

)