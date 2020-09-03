package com.codingblocks.weatherapp.data.source.remote.retrofit

import com.codingblocks.weatherapp.data.source.remote.retrofit.ApiEndPoints.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(
            WeatherApiService::class.java
        )
    }
}