package com.example.app_previtempo2024.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: WeatherAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.hgbrasil.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}

//https://api.open-meteo.com/v1/forecast?latitude=-23.5475,-22.9064&longitude=-46.6361,-43.1822&hourly=temperature_2m,relative_humidity_2m&timezone=America%2FSao_Paulo