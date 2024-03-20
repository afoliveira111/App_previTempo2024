package com.example.app_previtempo2024.view


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.app_previtempo2024.R
import com.example.app_previtempo2024.viewModel.WeatherViewModel
import com.example.app_previtempo2024.model.CityLocation


@Composable
fun WeatherScreen(viewModel: WeatherViewModel, navController: NavController) {
    val weatherData by viewModel.weatherData.collectAsState()
    val cidadesBrasil = listOf(
        CityLocation("São Paulo", -23.5505, -46.6333),
        CityLocation("Rio de Janeiro", -22.9068, -43.1729),
        CityLocation("Salvador", -12.9777, -38.4775)
    )

    val cidadeAtual = remember { mutableIntStateOf(0) }

    if (weatherData == null) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        weatherData?.let { data ->
            val gradientColors = if (data.isDay) {
                listOf(Color(0xFF415D77), Color(0xFF415D77)) // Exemplo de cores para o dia
            } else {
                listOf(Color(0xFF415D77), Color(0xFF415D77)) // Exemplo de cores para a noite
            }

            Box(
                modifier = Modifier
                    .background(Brush.verticalGradient(colors = gradientColors))
                    .fillMaxSize()
            ) {

                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = data.city,
                        style = MaterialTheme.typography.h4,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)

                    )

                    // Ícones de dia e noite em res/drawable
                    val iconId = if (data.isDay) R.drawable.ic_day else R.drawable.ic_night
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "Ícone do tempo",
                        modifier = Modifier.size(52.dp).padding(8.dp),
                        tint = Color.White // Assumindo que você queira uma coloração específica
                    )

                    // Apresentando os dados meteorológicos em Cards para cada seção
                    WeatherInfoCard("Temperatura", "${data.temperature}°C")
                    WeatherInfoCard("Umidade", "${data.humidity}%")
                    WeatherInfoCard("Probabilidade de chuva", "${data.precipitationProbability}%")
                    WeatherInfoCard("Amanhecer", data.sunrise)
                    WeatherInfoCard("Pôr do sol", data.sunset)
                    WeatherInfoCard("Horário", data.time)
                    WeatherInfoCard("Data", data.date)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            cidadeAtual.value = (cidadeAtual.value + 1) % cidadesBrasil.size
                            val cidadeSelecionada = cidadesBrasil[cidadeAtual.value]
                            viewModel.updateLocationAndFetchWeather(cidadeSelecionada.latitude, cidadeSelecionada.longitude)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .border(2.dp, Color.Transparent)
                            .background(Color.Transparent)
                    ) {
                        Text(
                            "Trocar para ${cidadesBrasil[(cidadeAtual.value + 1) % cidadesBrasil.size].name}",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherInfoCard(label: String, value: String) {
    Card(
        backgroundColor = Color.Transparent,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, style = MaterialTheme.typography.h6, color = Color.White)
            Text(text = value, style = MaterialTheme.typography.body1, color = Color.White)
        }
    }
}
