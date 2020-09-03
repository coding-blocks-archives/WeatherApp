package com.codingblocks.weatherapp.data.model

import java.io.Serializable

data class NetworkWeatherDescription(
    val id: Long,
    val main: String?,
    val description: String?,
    val icon: String?
) : Serializable
