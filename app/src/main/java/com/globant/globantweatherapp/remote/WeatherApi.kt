package com.globant.globantweatherapp.remote

import com.globant.globantweatherapp.data.CurrentWeather
import com.globant.globantweatherapp.data.ForeCastWeather
import com.globant.globantweatherapp.utils.NetworkService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApi @Inject constructor(private val service: Service) {

    fun getCurrentWeatherInfo(latitude: Double, longitude: Double) =
        service.getCurrentWeatherInfo(latitude, longitude)

    fun getThreeHoursForecast(city: String) = service.getThreeHoursForecast(city)

    fun getDailyForecastData(latitude: Double, longitude: Double) =
        service.getDailyForecastData(latitude, longitude)

    interface Service {
        @GET(NetworkService.CURRENT_WEATHER_END_POINT)
        fun getCurrentWeatherInfo(
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("APPID") apiKey: String = NetworkService.API_KEY,
            @Query("units") units: String = NetworkService.UNITS,
        ): Call<CurrentWeather>

        @GET(NetworkService.FORECAST_END_POINT)
        fun getDailyForecastData(
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("APPID") apiKey: String = NetworkService.API_KEY,
            @Query("units") units: String = NetworkService.UNITS,
        ): Call<ForeCastWeather>

        @GET(NetworkService.FORECAST_END_POINT)
        fun getThreeHoursForecast(
            @Query("q") cityName: String,
            @Query("APPID") apiKey: String = NetworkService.API_KEY,
            @Query("units") units: String = NetworkService.UNITS,
        ): Call<ForeCastWeather>

    }
}