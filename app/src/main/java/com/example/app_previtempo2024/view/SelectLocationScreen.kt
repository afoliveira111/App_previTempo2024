package com.example.app_previtempo2024.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_previtempo2024.viewModel.WeatherViewModel
import com.example.app_previtempo2024.model.CityLocation


@Composable
fun SelectLocationScreen(navController: NavController, viewModel: WeatherViewModel = viewModel()) {
    // Lista de cidades disponíveis para seleção
    val locations = listOf(
        CityLocation(name = "São Paulo", latitude = -23.5505, longitude = -46.6333),
        CityLocation(name = "Rio de Janeiro", latitude = -22.9068, longitude = -43.1729)
    )

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Selecione uma localização:", style = MaterialTheme.typography.h5)

            locations.forEach { location ->
                Button(
                    onClick = {
                        viewModel.updateLocationAndFetchWeather(location.latitude, location.longitude)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(location.name)
                }
            }
        }
    }
}