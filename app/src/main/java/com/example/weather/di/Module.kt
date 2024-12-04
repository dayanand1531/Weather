package com.example.weather.di

import android.content.Context
import androidx.room.Room
import com.example.weather.database.WeatherDao
import com.example.weather.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = Level.BODY
    }

    val okHttpClient = Builder()
        .addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): WeatherDatabase {
        return Room.databaseBuilder(app, WeatherDatabase::class.java, "weather_database").build()
    }

    @Provides
    fun provideUserDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

}