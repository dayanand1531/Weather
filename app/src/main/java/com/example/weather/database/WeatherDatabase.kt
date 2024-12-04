package com.example.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.data.Condition
import com.example.weather.data.Current
import com.example.weather.data.Location

@Database(entities = [Condition::class, Current::class, Location::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}