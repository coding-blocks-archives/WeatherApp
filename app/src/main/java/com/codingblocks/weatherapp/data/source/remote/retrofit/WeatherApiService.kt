package com.codingblocks.weatherapp.data.source.remote.retrofit

import com.codingblocks.weatherapp.data.model.NetworkWeather
import com.codingblocks.weatherapp.data.model.NetworkWeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    /**
     * This function gets the [NetworkWeather] for the [location] the
     * user searched for.
     */
    @GET("/data/2.5/weather")
    suspend fun getSpecificWeather(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): Response<NetworkWeather>

    // This function gets the weather information for the user's location.
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Response<NetworkWeather>

    // This function gets the weather forecast information for the user's location.
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String
    ): Response<NetworkWeatherForecastResponse>
}