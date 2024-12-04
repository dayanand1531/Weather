package com.example.weather.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import com.example.weather.R
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weather.di.ConnectivityUtil
import com.example.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherApp(
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = hiltViewModel(), innerPadding: PaddingValues) {
    val context = LocalContext.current
    val error = weatherViewModel.error.collectAsState()
    val isLoading = weatherViewModel.isLoading.collectAsState()
    val weatherInfo = weatherViewModel.weatherInfo.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    var city = remember { mutableStateOf("") }
    val iconUrl = weatherInfo.value?.icon
    val painter = rememberAsyncImagePainter(
        model = "https://$iconUrl",
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(R.drawable.ic_launcher_foreground)
    )

    ConstraintLayout(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    )
    {
        val (searchTextField, searchButton, weatherText, weatherImage, humidityText, conditionText, locationText, timeText) = createRefs()

        if (isLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        TextField(modifier = Modifier.constrainAs(searchTextField) {
            top.linkTo(parent.top, 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            value = city.value,
            onValueChange = {
                city.value = it
                weatherViewModel.getLocation(it)
            },
            label = { Text("Enter city") }
        )
        Button(onClick =
        {
            if (ConnectivityUtil.isInternetAvailable(context)) weatherViewModel.fetchWeather()
            else {
                coroutineScope.launch {
                    weatherViewModel.getWeatherInfoByDb()
                }
                Toast.makeText(context, "Internet not connected.", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.constrainAs(searchButton) {
                top.linkTo(searchTextField.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        { Text("Fetch Weather") }

        error.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            weatherViewModel.clearError()
        }

        weatherInfo.value?.temp_c?.let { temp ->
            Text(modifier = Modifier.constrainAs(locationText) {
                top.linkTo(searchButton.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)

            }, text = "Location: ${weatherInfo.value?.name}")

            Text(modifier = Modifier.constrainAs(timeText) {
                top.linkTo(locationText.top)
                bottom.linkTo(locationText.bottom)
                end.linkTo(parent.end, 16.dp)
            }, text = "Time: ${weatherInfo.value?.localtime}")

            Text(
                modifier = Modifier.constrainAs(weatherText) {
                    top.linkTo(timeText.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = "Temperature: ${temp}°C"
            )

            Image(
                painter = painter,
                contentDescription = "weather icon",
                modifier = Modifier
                    .constrainAs(weatherImage) {
                        top.linkTo(weatherText.top)
                        bottom.linkTo(weatherText.bottom)
                        start.linkTo(weatherText.end, 16.dp)
                    }
                    .size(width = 25.dp, height = 25.dp)
            )
            Text(modifier = Modifier.constrainAs(conditionText) {
                top.linkTo(weatherImage.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Condition: ${weatherInfo.value?.text}")

            Text(modifier = Modifier.constrainAs(humidityText) {
                top.linkTo(conditionText.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "Humidity: ${weatherInfo.value?.humidity}%")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {
        WeatherApp(
            innerPadding = TODO()
        )
    }
}