package com.globant.globantweatherapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.globantweatherapp.data.CurrentWeather
import com.globant.globantweatherapp.data.ForeCastWeather
import com.globant.globantweatherapp.forecast.ForeCastWeatherResult
import com.globant.globantweatherapp.location.GetLocationUseCase
import com.globant.globantweatherapp.remote.WeatherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: WeatherApi, private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {

    private var _isRefreshing = MutableStateFlow(false)
    var isRefreshing = _isRefreshing.asStateFlow()

    private var _currentWeatherInfo =
        MutableStateFlow<HomeWeatherResult>(HomeWeatherResult.Loading)

    var currentWeatherInfo = _currentWeatherInfo.asStateFlow()

    private var _hourlyForeCastData =
        MutableStateFlow<ForeCastWeatherResult>(ForeCastWeatherResult.Loading)

    var hourlyForeCastData = _hourlyForeCastData.asStateFlow()

    var cityName: String? = null

    fun getCurrentWeather() = viewModelScope.launch(Dispatchers.IO) {
        val currentLocation = getCurrentLocation.getLocation()
        currentLocation?.let { location ->
            service.getCurrentWeatherInfo(location.latitude, location.longitude)
                .enqueue(object : Callback, retrofit2.Callback<CurrentWeather> {
                    override fun onResponse(
                        call: Call<CurrentWeather>, response: Response<CurrentWeather>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                cityName = it.name
                                _currentWeatherInfo.value = HomeWeatherResult.Success(it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                        _currentWeatherInfo.value = HomeWeatherResult.Error("")
                    }
                })
        }
    }

    fun getThreeHoursForecast(city: String) = viewModelScope.launch(Dispatchers.IO) {
        service.getThreeHoursForecast(city)
            .enqueue(object : Callback, retrofit2.Callback<ForeCastWeather> {
                override fun onResponse(
                    call: Call<ForeCastWeather>,
                    response: Response<ForeCastWeather>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _hourlyForeCastData.value = ForeCastWeatherResult.Success(it)
                            _isRefreshing.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<ForeCastWeather>, t: Throwable) {
                    _hourlyForeCastData.value = ForeCastWeatherResult.Error("")
                    _isRefreshing.value = false
                }
            })
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentWeather()
            _isRefreshing.emit(true)
        }
    }
}
