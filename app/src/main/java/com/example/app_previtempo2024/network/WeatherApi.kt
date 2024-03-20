package com.example.app_previtempo2024.network

import com.example.app_previtempo2024.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call


interface WeatherAPI {
    @GET("/weather")
    fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m"
    ): Call<WeatherApiResponse>
}


// /weather?woeid=455825

// https://api.openweathermap.org/data/2.5/weather?