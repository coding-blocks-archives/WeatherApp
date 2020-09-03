package com.codingblocks.weatherapp.data.source.remote

import com.codingblocks.weatherapp.data.model.LocationModel
import com.codingblocks.weatherapp.data.model.NetworkWeather
import com.codingblocks.weatherapp.data.model.NetworkWeatherForecast
import com.codingblocks.weatherapp.utils.Result

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Result<NetworkWeather>

    suspend fun getWeatherForecast(cityId: Int): Result<List<NetworkWeatherForecast>>

    suspend fun getSearchWeather(query: String): Result<NetworkWeather>
}