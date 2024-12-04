package com.example.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.Condition
import com.example.weather.data.Current
import com.example.weather.data.Location
import com.example.weather.data.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert
    suspend fun insertCurrent(current: Current)

    @Insert
    suspend fun insertCondition(condition: Condition)

    @Query("update location set country = :country, lat = :lat, localtime = :localtime, localtime_epoch = :localtime_epoch, lon = :lon, name = :name, region = :region, tz_id = :tz_id where id = :id")
    suspend fun updateLocation(
        country: String,
        lat: Double,
        localtime: String,
        localtime_epoch: Int,
        lon: Double,
        name: String,
        region: String,
        tz_id: String,
        id: Int
    ): Int

    @Query("update current set temp_c = :temp_c, humidity = :humidity, temp_f = :temp_f where id = :id")
    suspend fun updateCurrent(temp_c: Double, humidity: Int, temp_f: Double, id: Int): Int

    @Query("update condition set text = :text, icon = :icon, code = :code where id = :id")
    fun updateCondition(text: String, icon: String, code: Int, id: Int): Int

    @Query("SELECT COUNT(*) FROM current WHERE id = :id")
    suspend fun isCurrentExists(id: Int): Boolean

    @Query("SELECT COUNT(*) FROM current WHERE id = :id")
    suspend fun isLocationExists(id: Int): Boolean

    @Query("SELECT COUNT(*) FROM current WHERE id = :id")
    suspend fun isConditionExists(id: Int): Boolean

    @Query(
        "SELECT location.name, current.humidity, current.temp_c, location.localtime, condition.icon, condition.text\n" +
                "FROM current\n" +
                "INNER JOIN condition ON current.id = condition.id\n" +
                "INNER JOIN location ON current.id = location.id"
    )
    fun getWeatherInfo(): Flow<WeatherInfo>
}