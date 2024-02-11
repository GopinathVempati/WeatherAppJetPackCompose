package com.globant.globantweatherapp.forecast

import com.globant.globantweatherapp.data.ForeCastWeather

sealed interface ForeCastWeatherResult {
    data class Success(val forecast: ForeCastWeather): ForeCastWeatherResult
    data class Error(val errorMessage: String?): ForeCastWeatherResult

    object Loading: ForeCastWeatherResult
}