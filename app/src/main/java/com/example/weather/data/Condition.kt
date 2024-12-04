package com.example.weather.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Condition(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: Int,
    val icon: String,
    val text: String
)