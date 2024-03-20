package com.example.app_previtempo2024.repository

import com.example.app_previtempo2024.network.WeatherAPI
import com.example.app_previtempo2024.model.WeatherApiResponse
import retrofit2.Callback


class WeatherRepository(private val weatherAPI: WeatherAPI) {
    fun getWeatherForecast(latitude: Double, longitude: Double, callback: Callback<WeatherApiResponse>) {
        weatherAPI.getWeatherForecast(latitude, longitude).enqueue(callback)
    }
}
