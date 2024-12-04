package com.example.weather.view

import android.util.Log
import com.example.weather.data.WeatherInfo
import com.example.weather.data.WeatherResponse
import com.example.weather.database.WeatherDao
import com.example.weather.di.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = weatherApi.getWeather(city, "734e00dc48b84a089fe114723240212")
            if (response.isSuccessful) {
                response.body()?.let { insertWeather(it) }
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertWeather(weather: WeatherResponse) {
        withContext(Dispatchers.IO) {
            val isCurrentExists = weatherDao.isCurrentExists(1)
            val isLocationExists = weatherDao.isLocationExists(1)
            val isConditionExists = weatherDao.isConditionExists(1)
            if (isCurrentExists && isLocationExists && isConditionExists) {
                try {
                    val locationId = weatherDao.updateLocation(
                        country = weather.location.country,
                        lat = weather.location.lat,
                        localtime = weather.location.localtime,
                        localtime_epoch = weather.location.localtime_epoch,
                        lon = weather.location.lon,
                        name = weather.location.name,
                        region = weather.location.region,
                        tz_id = weather.location.tz_id,
                        1
                    )
                    val conditionId = weatherDao.updateCondition(
                        text = weather.current.condition.text,
                        icon = weather.current.condition.icon,
                        code = weather.current.condition.code,
                        1
                    )
                    val updateId = weatherDao.updateCurrent(
                        temp_c = weather.current.temp_c,
                        humidity = weather.current.humidity,
                        temp_f = weather.current.temp_f,
                        1
                    )
                    Log.d(
                        "TAG",
                        "insertWeather: location $locationId  condition $conditionId currentId $updateId"
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "insertWeather: $e")
                }

            } else {
                weatherDao.insertLocation(weather.location)
                weatherDao.insertCondition(weather.current.condition)
                weatherDao.insertCurrent(weather.current)
            }
        }
    }

    val getWeatherInfo: Flow<WeatherInfo> = weatherDao.getWeatherInfo()

}