package com.globant.globantweatherapp.utils

object NetworkService {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "dac5712150a8a90c7205a239e345f9a4"
    const val UNITS: String = "metric"
    const val FORECAST_END_POINT = "/data/2.5/forecast"
    const val CURRENT_WEATHER_END_POINT = "/data/2.5/weather"
}

object ExceptionTitles {
    const val GPS_DISABLED = "GPS Disabled"
    const val NO_PERMISSION = "No Permission"
}

object AppStrings {
    const val metric = "mph"
    const val degree = "°"
}
