package com.example.weather.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherInfo
import com.example.weather.data.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {
    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> get() = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _locationSearch = MutableStateFlow("")
    val locationSearch: StateFlow<String?> get() = _locationSearch

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    var _weatherInfo = MutableStateFlow<WeatherInfo?>(null)
    val weatherInfo: StateFlow<WeatherInfo?> get() = _weatherInfo

    fun getLocation(searchLocation: String) {
        _locationSearch.value = searchLocation
    }

    fun fetchWeather() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = _locationSearch.value.let { repository.getWeather(it) }
            result.onSuccess {
                _weather.value = it
                _error.value = null
                _isLoading.value = false

            }.onFailure {
                _error.value = it.message?.let { errorResponse -> getErrorMessage(errorResponse) }
                _weather.value = null
                _isLoading.value = false
                getWeatherInfoByDb()
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    suspend fun getWeatherInfoByDb() {
        repository.getWeatherInfo.collect {
            _weatherInfo.value = it
        }
    }

    fun getErrorMessage(errorResponse: String): String {
        val jsonObject = JSONObject(errorResponse)
        val errorObject = jsonObject.getJSONObject("error")
        val message = errorObject.getString("message")
        return message
    }
}