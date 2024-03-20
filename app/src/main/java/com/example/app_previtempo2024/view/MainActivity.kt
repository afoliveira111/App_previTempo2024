package com.example.app_previtempo2024.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_previtempo2024.network.RetrofitInstance
import com.example.app_previtempo2024.repository.WeatherRepository
import com.example.app_previtempo2024.viewModel.WeatherViewModel
import com.example.app_previtempo2024.viewModel.WeatherViewModelFactory
import com.example.app_previtempo2024.ui.theme.App_previTempo2024Theme

//import com.github.oxeanbits.redukt.StateListener
//import com.github.oxeanbits.redukt.Redukt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherAPI = RetrofitInstance.api
        val weatherRepository = WeatherRepository(weatherAPI)
        val viewModelFactory = WeatherViewModelFactory(weatherRepository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        setContent {
            App_previTempo2024Theme {
                MainWeatherScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainWeatherScreen(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "weather") {
        composable("weather") {
            // Passando o viewModel como argumento para WeatherScreen
            WeatherScreen(viewModel = viewModel, navController = navController)
        }
        composable("selectLocation") {
            // Se SelectLocationScreen precisa do viewModel, passe ele aqui também
            SelectLocationScreen(navController = navController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_previTempo2024Theme {
        Greeting("Android")
    }
}

//Redux
sealed class WeatherAction {
    data class UpdateLocationAndFetchWeather(
        val latitude: Double, val longitude: Double
    ) : WeatherAction()
}