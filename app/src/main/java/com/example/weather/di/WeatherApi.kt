package com.example.weather.di

import com.example.weather.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String
    ): Response<WeatherResponse>
}