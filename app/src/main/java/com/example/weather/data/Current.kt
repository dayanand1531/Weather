package com.example.weather.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("current")
data class Current(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("dewpoint_c") val dewpoint_c: Double,
    @SerializedName("dewpoint_f") val dewpoint_f: Double,
    @SerializedName("feelslike_c") val feelslike_c: Double,
    @SerializedName("feelslike_f") val feelslike_f: Double,
    @SerializedName("gust_kph") val gust_kph: Double,
    @SerializedName("gust_mph") val gust_mph: Double,
    @SerializedName("heatindex_c") val heatindex_c: Double,
    @SerializedName("heatindex_f") val heatindex_f: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("is_day") val is_day: Int,
    @SerializedName("last_updated") val last_updated: String,
    @SerializedName("last_updated_epoch") val last_updated_epoch: Int,
    @SerializedName("precip_in") val precip_in: Double,
    @SerializedName("precip_mm") val precip_mm: Double,
    @SerializedName("pressure_in") val pressure_in: Double,
    @SerializedName("pressure_mb") val pressure_mb: Double,
    @SerializedName("temp_c") val temp_c: Double,
    @SerializedName("temp_f") val temp_f: Double,
    @SerializedName("uv") val uv: Double,
    @SerializedName("vis_km") val vis_km: Double,
    @SerializedName("vis_miles") val vis_miles: Double,
    @SerializedName("wind_degree") val wind_degree: Int,
    @SerializedName("wind_dir") val wind_dir: String,
    @SerializedName("wind_kph") val wind_kph: Double,
    @SerializedName("wind_mph") val wind_mph: Double,
    @SerializedName("windchill_c") val windchill_c: Double,
    @SerializedName("windchill_f") val windchill_f: Double
) {
    @Ignore
    @SerializedName("condition")
    lateinit var condition: Condition

    constructor() : this(
        id = 0,
        cloud = 0,
        dewpoint_c = 0.0,
        dewpoint_f = 0.0,
        feelslike_c = 0.0,
        feelslike_f = 0.0,
        gust_kph = 0.0,
        gust_mph = 0.0,
        heatindex_c = 0.0,
        heatindex_f = 0.0,
        humidity = 0,
        is_day = 0,
        last_updated = "",
        last_updated_epoch = 0,
        precip_in = 0.0,
        precip_mm = 0.0,
        pressure_in = 0.0,
        pressure_mb = 0.0,
        temp_c = 0.0,
        temp_f = 0.0,
        uv = 0.0,
        vis_km = 0.0,
        vis_miles = 0.0,
        wind_degree = 0,
        wind_dir = "",
        wind_kph = 0.0,
        wind_mph = 0.0,
        windchill_c = 0.0,
        windchill_f = 0.0
    )
}