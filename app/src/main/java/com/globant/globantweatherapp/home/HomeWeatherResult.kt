package com.globant.globantweatherapp.home

import com.globant.globantweatherapp.data.CurrentWeather

sealed interface HomeWeatherResult {
    data class Success(val currentWeather: CurrentWeather): HomeWeatherResult
    data class Error(val errorMessage: String?): HomeWeatherResult

    object Loading: HomeWeatherResult
}