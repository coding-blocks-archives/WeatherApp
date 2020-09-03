package com.codingblocks.weatherapp.ui.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.codingblocks.weatherapp.data.model.LocationModel
import com.codingblocks.weatherapp.data.model.Weather
import com.codingblocks.weatherapp.data.source.repository.WeatherRepository
import com.codingblocks.weatherapp.utils.LocationLiveData
import com.codingblocks.weatherapp.utils.Result.*
import com.codingblocks.weatherapp.utils.asLiveData
import com.codingblocks.weatherapp.utils.convertKelvinToCelsius
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val repository: WeatherRepository,
    application: Application
) : ViewModel() {
    private val locationLiveData = LocationLiveData(application)

    init {
        currentSystemTime()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _weather = MutableLiveData<Weather?>()
    val weather = _weather.asLiveData()

    val time = currentSystemTime()

    fun getLocationLiveData() = locationLiveData

    /**
     *This attempts to get the [Weather] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeather
     */
    fun getWeather(location: LocationModel) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = repository.getWeather(location, false)) {
                is Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val weather = result.data
                        _dataFetchState.value = true
                        _weather.value = weather
                    } else {
                        refreshWeather(location)
                    }
                }
                is Error -> {
                    _isLoading.value = false
                    _dataFetchState.value = false
                }

                is Loading -> _isLoading.postValue(true)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun currentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE MMM d, hh:mm aaa")
        return dateFormat.format(date)
    }

    /**
     * This is called when the user swipes down to refresh.
     * This enables the [Weather] for the current [location] to be received.
     */
    fun refreshWeather(location: LocationModel) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getWeather(location, true)) {
                is Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val weather = result.data.apply {
                            this.networkWeatherCondition.temp = convertKelvinToCelsius(this.networkWeatherCondition.temp)
                        }
                        _dataFetchState.value = true
                        _weather.value = weather

                        repository.deleteWeatherData()
                        repository.storeWeatherData(weather)
                    } else {
                        _weather.postValue(null)
                        _dataFetchState.postValue(false)
                    }
                }
                is Error -> {
                    _isLoading.value = false
                    _dataFetchState.value = false
                }
                is Loading -> _isLoading.postValue(true)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class HomeFragmentViewModelFactory(
        private val repository: WeatherRepository,
        private val application: Application
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            (HomeViewModel(repository, application) as T)
    }
}