package com.codingblocks.weatherapp.data.model

import java.io.Serializable

data class NetworkWeatherCondition(
    var temp: Double,
    val pressure: Double,
    val humidity: Double
) : Serializable
