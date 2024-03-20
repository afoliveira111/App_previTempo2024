package com.example.app_previtempo2024.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_previtempo2024.repository.WeatherRepository
import com.example.app_previtempo2024.model.WeatherApiResponse
import com.example.app_previtempo2024.model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData


    init {
        // Exemplo: atualizar os dados ao iniciar a ViewModel com coordenadas de São Paulo:
        updateLocationAndFetchWeather(-23.5505, -46.6333)
        updateLocationAndFetchWeather(-22.9068, -43.1729)
        updateLocationAndFetchWeather(-12.9716, -38.4775)
    }


    fun updateLocationAndFetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.getWeatherForecast(latitude, longitude, object :
                Callback<WeatherApiResponse> {
                override fun onResponse(
                    call: Call<WeatherApiResponse>,
                    response: Response<WeatherApiResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { apiResponse ->
                            _weatherData.value = transformToWeatherData(apiResponse)
                        } ?: run {
                            Log.e("WeatherViewModel", "Resposta bem-sucedida, mas sem corpo")
                            _weatherData.value = null
                        }
                    } else {
                        Log.e(
                            "WeatherViewModel",
                            "Resposta não bem-sucedida: ${response.errorBody()?.string()}"
                        )
                        _weatherData.value = null
                    }
                }

                override fun onFailure(call: Call<WeatherApiResponse>, t: Throwable) {
                    Log.e("WeatherViewModel", "Falha na chamada da API", t)
                    _weatherData.value = null
                }
            })
        }
    }

    private fun transformToWeatherData(apiResponse: WeatherApiResponse): WeatherData {
        // Dados diretamente da resposta da API
        val temperature = apiResponse.results.temp
        val humidity = apiResponse.results.humidity
        val description = apiResponse.results.description
        val isDay = apiResponse.results.currently == "dia"
        val date = apiResponse.results.date
        val time = apiResponse.results.time
        val sunrise = apiResponse.results.sunrise
        val sunset = apiResponse.results.sunset
        val precipitationProbability =
            apiResponse.results.forecast.firstOrNull()?.rainProbability ?: 0
        val windSpeedy = apiResponse.results.windSpeedy
        val city = apiResponse.results.city

        return WeatherData(
            temperature = temperature,
            humidity = humidity,
            description = description,
            isDay = isDay,
            date = date,
            time = time,
            sunrise = sunrise,
            sunset = sunset,
            precipitationProbability = precipitationProbability,
            windSpeedy = windSpeedy,
            city = city,
        )
    }
}
