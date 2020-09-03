package com.codingblocks.weatherapp.data.source.local

import com.codingblocks.weatherapp.data.source.local.dao.WeatherDao
import com.codingblocks.weatherapp.data.source.local.entity.DBWeather
import com.codingblocks.weatherapp.data.source.local.entity.DBWeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherLocalDataSource {
    override suspend fun getWeather(): DBWeather? = withContext(ioDispatcher) {
        return@withContext weatherDao.getWeather()
    }

    override suspend fun saveWeather(weather: DBWeather) = withContext(ioDispatcher) {
        weatherDao.insertWeather(weather)
    }

    override suspend fun deleteWeather() = withContext(ioDispatcher) {
        weatherDao.deleteAllWeather()
    }

    override suspend fun getForecastWeather(): List<DBWeatherForecast>? =
        withContext(ioDispatcher) {
            return@withContext weatherDao.getAllWeatherForecast()
        }

    override suspend fun saveForecastWeather(weatherForecast: DBWeatherForecast) =
        withContext(ioDispatcher) {
            weatherDao.insertForecastWeather(weatherForecast)
        }

    override suspend fun deleteForecastWeather() = withContext(ioDispatcher) {
        weatherDao.deleteAllWeatherForecast()
    }
}